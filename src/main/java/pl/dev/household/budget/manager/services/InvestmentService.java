package pl.dev.household.budget.manager.services;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;
import pl.dev.household.budget.manager.dao.Investment;
import pl.dev.household.budget.manager.dao.User;
import pl.dev.household.budget.manager.dao.repository.InvestmentRepository;
import pl.dev.household.budget.manager.domain.InvestmentDTO;
import pl.dev.household.budget.manager.domain.ReportIntDTO;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Period;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Slf4j
@Service
public class InvestmentService {

    private ModelMapper modelMapper;
    private InvestmentRepository investmentRepository;
    private UserService userService;

    public InvestmentService(ModelMapper modelMapper, InvestmentRepository investmentRepository, UserService userService) {
        this.modelMapper = modelMapper;
        this.investmentRepository = investmentRepository;
        this.userService = userService;
    }

    public List<InvestmentDTO> getInvestments(Integer householdId) throws Exception {
        return getInvestmentsForHouseholdUsers(householdId);
    }

    public InvestmentDTO getInvestment(Integer investmentId) {
        return modelMapper.map(investmentRepository.findById(investmentId), InvestmentDTO.class);
    }

    public InvestmentDTO addInvestment(InvestmentDTO investmentDTO) {
        Integer investmentId = investmentRepository.save(modelMapper.map(investmentDTO, Investment.class)).getId();
        return getInvestment(investmentId);
    }

    public void updateInvestment(Integer userId, InvestmentDTO investmentDTO) {
        Investment updatedInvestment = modelMapper.map(investmentDTO, Investment.class);

        if (updatedInvestment.getUser() == null) {
            updatedInvestment.setUser(modelMapper.map(userService.getUserDetails(userId), User.class));
        }

        investmentRepository.save(updatedInvestment);
    }

    public ReportIntDTO countInvestmentBalance(Integer householdId) throws Exception {
        ReportIntDTO report = new ReportIntDTO();
        BigDecimal incomeTmp = BigDecimal.valueOf(0);

        List<InvestmentDTO> investmentsList = aggregateInvestments(householdId);

        if (investmentsList != null && !investmentsList.isEmpty()) {
            for (InvestmentDTO investment : investmentsList) {
                incomeTmp = incomeTmp.add(investment.getAmount().multiply(investment.getInvestmentPercentage()).divide(BigDecimal.valueOf(12), 2, RoundingMode.HALF_UP));
            }
        }

        report.setIncome(incomeTmp);
        return report;
    }

    public List<InvestmentDTO> aggregateInvestmentsForCurrentMonth(Integer householdId) throws Exception {
        return aggregateInvestments(householdId);
    }

    private List<InvestmentDTO> getInvestmentsForHouseholdUsers(Integer householdId) throws Exception {
        List<User> usersInHousehold = userService.getAllUsersInHouseholdByHousehold(householdId);

        if (usersInHousehold.isEmpty()) {
            throw new Exception("No users in household found!");
        }

        List<Investment> investmentList = new ArrayList<>();

        usersInHousehold.forEach(user -> {
            investmentList.addAll(investmentRepository.findAllByUserId(user.getId()).orElse(Collections.emptyList()));
        });

        return modelMapper.map(investmentList, new TypeToken<List<InvestmentDTO>>() {
        }.getType());
    }

    private List<InvestmentDTO> aggregateInvestments(Integer householdId) throws Exception {
        return getInvestmentsForHouseholdUsers(householdId).stream()
                .filter(investment -> YearMonth.now().atEndOfMonth().isBefore(investment.getEndDate()))
                .filter(checkIfMonthIsPeriodicForInvestments())
                .collect(Collectors.toList());
    }

    private static Predicate<InvestmentDTO> checkIfMonthIsPeriodicForInvestments() {
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
}
