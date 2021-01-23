package pl.dev.household.budget.manager.services;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;
import pl.dev.household.budget.manager.dao.Balance;
import pl.dev.household.budget.manager.dao.Cashflow;
import pl.dev.household.budget.manager.dao.User;
import pl.dev.household.budget.manager.dao.repository.CashflowRepository;
import pl.dev.household.budget.manager.dao.repository.HouseholdRepository;
import pl.dev.household.budget.manager.dao.repository.UserRepository;
import pl.dev.household.budget.manager.dictionaries.CashflowCategory;
import pl.dev.household.budget.manager.domain.BalanceDTO;
import pl.dev.household.budget.manager.domain.CashflowDTO;
import pl.dev.household.budget.manager.domain.ReportIntDTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CashflowService {

    private ModelMapper modelMapper;
    private CashflowRepository cashflowRepository;
    private UserRepository userRepository;
    private HouseholdRepository householdRepository;
    private UserService userService;

    public CashflowService(
            ModelMapper modelMapper,
            CashflowRepository cashflowRepository,
            UserRepository userRepository,
            HouseholdRepository householdRepository,
            UserService userService
    ) {
        this.modelMapper = modelMapper;
        this.cashflowRepository = cashflowRepository;
        this.userRepository = userRepository;
        this.householdRepository = householdRepository;
        this.userService = userService;
    }

    public List<CashflowDTO> getCashflows(Integer householdId) throws Exception {
        return getCashflowForHouseholdUsers(householdId);
    }

//    public List<CashflowDTO> getCashflowsWithType(Integer userId, CashflowCategory cashflowCategory) {
//        Optional<List<Cashflow>> optList = cashflowRepository.findAllByUserIdAndPeriodNotLike(userId, 0);
//
//        return getCashflowForHouseholdUsers().stream()
//                .flatMap(Collection::stream)
//                .map(cashflow ->
//                        modelMapper.map(cashflow, CashflowDTO.class)
//                ).filter(getCashflowWithCategoryPredicate(cashflowCategory))
//                .collect(Collectors.toList());
//    }

    public CashflowDTO getCashflow(Integer cashflowId) {
        return modelMapper.map(cashflowRepository.findById(cashflowId), CashflowDTO.class);
    }

    public CashflowDTO addCashflow(CashflowDTO cashflowDTO) {
        Integer cashflowId = cashflowRepository.save(modelMapper.map(cashflowDTO, Cashflow.class)).getId();
        return getCashflow(cashflowId);
    }


    public void updateCashflow(Integer userId, CashflowDTO cashflowDTO) {
        Cashflow tmpCashflow = modelMapper.map(cashflowDTO, Cashflow.class);

        if (tmpCashflow.getUser() == null) {
            tmpCashflow.setUser(userRepository.findById(userId).get());
        }

        cashflowRepository.save(tmpCashflow);
    }

    private Predicate<CashflowDTO> getCashflowWithCategoryPredicate(CashflowCategory cashflowCategory) {
        return cashflowDTO -> cashflowDTO.getCategory().equals(cashflowCategory);
    }

    public ReportIntDTO countCashflowBalance(Integer householdId) throws Exception {
        ReportIntDTO report = new ReportIntDTO();
        BigDecimal incomeTmp = BigDecimal.valueOf(0);
        BigDecimal burdenTmp = BigDecimal.valueOf(0);

        List<CashflowDTO> cashflowsList = aggregateCashflows(householdId);

        if (cashflowsList != null) {
            for (CashflowDTO cashflow : cashflowsList) {
                if (cashflow.getCategory().equals(CashflowCategory.INCOME)) {
                    incomeTmp = incomeTmp.add(cashflow.getAmount());
                } else {
                    burdenTmp = burdenTmp.add(cashflow.getAmount());
                }
            }
        }

        report.setIncome(incomeTmp);
        report.setBurden(burdenTmp);

        return report;
    }

    public List<CashflowDTO> aggregateCashflowForCurrentMonth(Integer householdId) throws Exception {
        return aggregateCashflows(householdId).stream().map(cashflow -> modelMapper.map(cashflow, CashflowDTO.class)).collect(Collectors.toList());
    }

    private List<CashflowDTO> getCashflowForHouseholdUsers(Integer householdId) throws Exception {
        List<User> usersInHousehold = userService.getAllUsersInHouseholdByHousehold(householdId);

        if (usersInHousehold.isEmpty()) {
            throw new Exception("No users in Household");
        }

        List<Cashflow> optList = new ArrayList<>();

        usersInHousehold.forEach(user -> optList.addAll(cashflowRepository.findAllByUserIdAndPeriodNotLike(user.getId(), 0).orElse(Collections.emptyList())));

        return modelMapper.map(optList, new TypeToken<List<CashflowDTO>>() {
        }.getType());
    }

    private List<CashflowDTO> aggregateCashflows(Integer householdId) throws Exception {

        return getCashflowForHouseholdUsers(householdId).stream()
                .filter(cashflow -> YearMonth.now().atEndOfMonth().isBefore(cashflow.getEndDate()))
                .filter(checkIfMonthIsPeriodicForCashflow())
                .collect(Collectors.toList());
    }

    private static Predicate<CashflowDTO> checkIfMonthIsPeriodicForCashflow() {
        return p -> {
            Period period = Period.between(p.getEndDate().minusMonths(12), LocalDate.now());
            int periodInt;
            if (period.getMonths() == 0) {
                periodInt = 1;
            } else {
                periodInt = period.getMonths();
            }

            return periodInt % p.getPeriod() == 0;
        };
    }

    public List<CashflowDTO> getCashflowsForHouseholdNoMonthAgo(Integer householdId, int no) {
        LocalDate startDate = LocalDate.now().minusMonths(no).withDayOfMonth(1);
        LocalDate endDate = LocalDate.now().minusMonths(no).withDayOfMonth(1).plusMonths(1).minusDays(1);

        List<User> usersInHousehold = userService.getAllUsersInHouseholdByHousehold(householdId);

        List<Cashflow> optList = new ArrayList<>();

        usersInHousehold.forEach(user -> {
            optList.addAll(cashflowRepository.findByUserIdAndStartDateIsGreaterThanEqualAndStartDateIsLessThanEqualAndPeriodLike(
                    user.getId(),
                    startDate,
                    endDate,
                    0
            ).orElse(Collections.emptyList()));
        });


        return sortCashflowList(modelMapper.map(optList, new TypeToken<List<CashflowDTO>>() {
        }.getType()));

    }

    private List<CashflowDTO> sortCashflowList(List<CashflowDTO> cashflowDTOList) {
        return cashflowDTOList.stream().sorted(Comparator.comparing(CashflowDTO::getStartDate)).collect(Collectors.toList());
    }
}
