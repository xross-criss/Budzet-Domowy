package pl.dev.household.budget.manager.services;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import pl.dev.household.budget.manager.dao.Investment;
import pl.dev.household.budget.manager.dao.repository.InvestmentRepository;
import pl.dev.household.budget.manager.domain.InvestmentDTO;
import pl.dev.household.budget.manager.domain.ReportIntDTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Slf4j
@Service
public class InvestmentService {

    private ModelMapper modelMapper;
    private InvestmentRepository investmentRepository;

    public InvestmentService(ModelMapper modelMapper, InvestmentRepository investmentRepository) {
        this.modelMapper = modelMapper;
        this.investmentRepository = investmentRepository;
    }

    public List<InvestmentDTO> getInvestments(Integer householdId) {
        return investmentRepository.findAllByHousehold_Id(householdId).stream()
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

    public InvestmentDTO updateInvestment(Integer householdId, InvestmentDTO investmentDTO) {
        Optional<Investment> oldInvestment = investmentRepository.findById(investmentDTO.getId());
        if (oldInvestment.isEmpty() || !oldInvestment.get().getId().equals(investmentDTO.getId())) {
            throw new RuntimeException("Investment cannot be updated!");
        }

        Investment updatedInvestment = modelMapper.map(investmentDTO, Investment.class);
        investmentRepository.save(updatedInvestment);

        return getInvestment(updatedInvestment.getId());
    }

    public ReportIntDTO countInvestmentBalance(Integer householdId) {
        ReportIntDTO report = new ReportIntDTO();
        BigDecimal incomeTmp = BigDecimal.valueOf(0);

        List<Investment> investmentsList = aggregateInvestments(householdId);

        if (investmentsList != null && !investmentsList.isEmpty()) {
            for (Investment investment : investmentsList) {
                incomeTmp = incomeTmp.add(investment.getAmount());
            }
        }

        report.setIncome(incomeTmp);
        return report;
    }

    public List<InvestmentDTO> aggregateInvestmentsForCurrentMonth(Integer householdId) {
        return aggregateInvestments(householdId).stream().map(investment -> modelMapper.map(investment, InvestmentDTO.class)).collect(Collectors.toList());
    }

    private List<Investment> aggregateInvestments(Integer householdId) {
        return investmentRepository.findAllByHousehold_Id(householdId).stream()
                .filter(investment -> investment.getEndDate().isBefore(YearMonth.now().atEndOfMonth()))
                .filter(checkIfMonthIsPeriodicForInvestments())
                .collect(Collectors.toList());
    }

    private static Predicate<Investment> checkIfMonthIsPeriodicForInvestments() {
        return p -> Period.between(p.getStartDate(), LocalDate.now()).getMonths() % p.getInterval() == 0;
    }
}
