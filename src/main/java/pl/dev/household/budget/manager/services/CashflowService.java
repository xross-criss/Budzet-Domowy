package pl.dev.household.budget.manager.services;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import pl.dev.household.budget.manager.dao.Cashflow;
import pl.dev.household.budget.manager.dao.repository.CashflowRepository;
import pl.dev.household.budget.manager.dao.repository.HouseholdRepository;
import pl.dev.household.budget.manager.dao.repository.UserRepository;
import pl.dev.household.budget.manager.dictionaries.CashflowCategory;
import pl.dev.household.budget.manager.domain.CashflowDTO;
import pl.dev.household.budget.manager.domain.ReportIntDTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.time.YearMonth;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CashflowService {

    private ModelMapper modelMapper;
    private CashflowRepository cashflowRepository;
    private UserRepository userRepository;
    private HouseholdRepository householdRepository;

    public CashflowService(ModelMapper modelMapper, CashflowRepository cashflowRepository, UserRepository userRepository, HouseholdRepository householdRepository) {
        this.modelMapper = modelMapper;
        this.cashflowRepository = cashflowRepository;
        this.userRepository = userRepository;
        this.householdRepository = householdRepository;
    }

    public List<CashflowDTO> getCashflows(Integer householdId) {
        Optional<List<Cashflow>> optList = cashflowRepository.findAllByHousehold_Id(householdId);

        return optList.stream()
                .flatMap(Collection::stream)
                .map(cashflow ->
                        modelMapper.map(cashflow, CashflowDTO.class)
                ).collect(Collectors.toList());
    }

    public List<CashflowDTO> getCashflowsWithType(Integer householdId, CashflowCategory cashflowCategory) {
        Optional<List<Cashflow>> optList = cashflowRepository.findAllByHousehold_Id(householdId);

        return optList.stream()
                .flatMap(Collection::stream)
                .map(cashflow ->
                        modelMapper.map(cashflow, CashflowDTO.class)
                ).filter(getCashflowWithCategoryPredicate(cashflowCategory))
                .collect(Collectors.toList());
    }

    public CashflowDTO getCashflow(Integer cashflowId) {
        return modelMapper.map(cashflowRepository.findById(cashflowId), CashflowDTO.class);
    }

    public CashflowDTO addCashflow(CashflowDTO cashflowDTO) {
        Integer cashflowId = cashflowRepository.save(modelMapper.map(cashflowDTO, Cashflow.class)).getId();
        return getCashflow(cashflowId);
    }


    public void updateCashflow(Integer householdId, CashflowDTO cashflowDTO) {
        Cashflow tmpCashflow = modelMapper.map(cashflowDTO, Cashflow.class);

        if (tmpCashflow.getHousehold() == null) {
            tmpCashflow.setHousehold(householdRepository.findById(householdId).get());
        }

        cashflowRepository.save(tmpCashflow);
    }

    private Predicate<CashflowDTO> getCashflowWithCategoryPredicate(CashflowCategory cashflowCategory) {
        return cashflowDTO -> cashflowDTO.getCategory().equals(cashflowCategory);
    }

    public ReportIntDTO countCashflowBalance(Integer householdId) {
        ReportIntDTO report = new ReportIntDTO();
        BigDecimal incomeTmp = BigDecimal.valueOf(0);
        BigDecimal burdenTmp = BigDecimal.valueOf(0);

        List<Cashflow> cashflowsList = aggregateCashflows(householdId);

        if (cashflowsList != null) {
            for (Cashflow cashflow : cashflowsList) {
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

    public List<CashflowDTO> aggregateCashflowForCurrentMonth(Integer householdId) {
        return aggregateCashflows(householdId).stream().map(cashflow -> modelMapper.map(cashflow, CashflowDTO.class)).collect(Collectors.toList());
    }

    private List<Cashflow> aggregateCashflows(Integer householdId) {
        Optional<List<Cashflow>> optList = Optional.of(cashflowRepository.findAllByHousehold_Id(householdId).orElse(Collections.emptyList()));

        return optList.stream()
                .flatMap(Collection::stream)
                .filter(cashflow -> YearMonth.now().atEndOfMonth().isBefore(cashflow.getEndDate()))
                .filter(checkIfMonthIsPeriodicForCashflow())
                .collect(Collectors.toList());
    }

    private static Predicate<Cashflow> checkIfMonthIsPeriodicForCashflow() {
        return p -> Period.between(p.getStartDate(), LocalDate.now()).getMonths() % p.getPeriod() == 0;
    }
}
