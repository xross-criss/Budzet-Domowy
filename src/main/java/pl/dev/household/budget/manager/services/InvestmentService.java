package pl.dev.household.budget.manager.services;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import pl.dev.household.budget.manager.dao.Investment;
import pl.dev.household.budget.manager.dao.repository.HouseholdRepository;
import pl.dev.household.budget.manager.dao.repository.InvestmentRepository;
import pl.dev.household.budget.manager.domain.InvestmentDTO;
import pl.dev.household.budget.manager.domain.ReportIntDTO;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
public class InvestmentService {

    private ModelMapper modelMapper;
    private InvestmentRepository investmentRepository;
    private HouseholdRepository householdRepository;

    public InvestmentService(ModelMapper modelMapper, InvestmentRepository investmentRepository, HouseholdRepository householdRepository) {
        this.modelMapper = modelMapper;
        this.investmentRepository = investmentRepository;
        this.householdRepository = householdRepository;
    }

    public List<InvestmentDTO> getInvestments(Integer householdId) {
        Optional<List<Investment>> optList = Optional.of(investmentRepository.findAllByHousehold_Id(householdId).orElse(Collections.emptyList()));

        return optList.stream()
                .flatMap(Collection::stream)
                .map(investment -> modelMapper.map(investment, InvestmentDTO.class))
                .collect(Collectors.toList());
    }

    public InvestmentDTO getInvestment(Integer investmentId) {
        return modelMapper.map(investmentRepository.findById(investmentId), InvestmentDTO.class);
    }

    public InvestmentDTO addInvestment(InvestmentDTO investmentDTO) {
        Integer investmentId = investmentRepository.save(modelMapper.map(investmentDTO, Investment.class)).getId();
        return getInvestment(investmentId);
    }

    public void updateInvestment(Integer householdId, InvestmentDTO investmentDTO) {
        Investment updatedInvestment = modelMapper.map(investmentDTO, Investment.class);

        if (updatedInvestment.getHousehold() == null) {
            updatedInvestment.setHousehold(householdRepository.findById(householdId).get());
        }

        investmentRepository.save(updatedInvestment);
    }

    public ReportIntDTO countInvestmentBalance(Integer householdId) {
        ReportIntDTO report = new ReportIntDTO();
        BigDecimal incomeTmp = BigDecimal.valueOf(0);

        List<Investment> investmentsList = aggregateInvestments(householdId);

        if (investmentsList != null && !investmentsList.isEmpty()) {
            for (Investment investment : investmentsList) {
                incomeTmp = incomeTmp.add(investment.getAmount().multiply(investment.getInvestmentPercentage()).divide(BigDecimal.valueOf(12), 2, RoundingMode.HALF_UP));
            }
        }

        report.setIncome(incomeTmp);
        return report;
    }

    public List<InvestmentDTO> aggregateInvestmentsForCurrentMonth(Integer householdId) {
        return aggregateInvestments(householdId).stream().map(investment -> modelMapper.map(investment, InvestmentDTO.class)).collect(Collectors.toList());
    }

    private List<Investment> aggregateInvestments(Integer householdId) {
        Optional<List<Investment>> optList = Optional.of(investmentRepository.findAllByHousehold_Id(householdId).orElse(Collections.emptyList()));

        return optList.stream()
                .flatMap(Collection::stream)
                .filter(investment -> YearMonth.now().atEndOfMonth().isBefore(investment.getEndDate()))
                .filter(checkIfMonthIsPeriodicForInvestments())
                .collect(Collectors.toList());
    }

    private static Predicate<Investment> checkIfMonthIsPeriodicForInvestments() {
        return p -> Period.between(p.getStartDate(), LocalDate.now()).getMonths() % p.getPeriod() == 0;
    }
}
