package pl.dev.household.budget.manager.services;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import pl.dev.household.budget.manager.dao.Loan;
import pl.dev.household.budget.manager.dao.repository.HouseholdRepository;
import pl.dev.household.budget.manager.dao.repository.LoanRepository;
import pl.dev.household.budget.manager.domain.LoanDTO;
import pl.dev.household.budget.manager.domain.ReportIntDTO;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class LoanService {

    private ModelMapper modelMapper;
    private LoanRepository loanRepository;
    private HouseholdRepository householdRepository;

    public LoanService(ModelMapper modelMapper, LoanRepository loanRepository, HouseholdRepository householdRepository) {
        this.modelMapper = modelMapper;
        this.loanRepository = loanRepository;
        this.householdRepository = householdRepository;
    }

    public List<LoanDTO> getLoans(Integer householdId) {
        Optional<List<Loan>> optList = Optional.of(loanRepository.findAllByHousehold_Id(householdId).orElse(Collections.emptyList()));

        return optList.stream()
                .flatMap(Collection::stream)
                .map(loan -> modelMapper.map(loan, LoanDTO.class))
                .collect(Collectors.toList());
    }

    public LoanDTO getLoan(Integer loanId) {
        return modelMapper.map(loanRepository.findById(loanId), LoanDTO.class);
    }

    public LoanDTO addLoan(LoanDTO loanDTO) {
        Integer loanId = loanRepository.save(modelMapper.map(loanDTO, Loan.class)).getId();
        return getLoan(loanId);
    }

    public void updateLoan(Integer householdId, LoanDTO loanDTO) {
        Loan updatedLoan = modelMapper.map(loanDTO, Loan.class);

        if (updatedLoan.getHousehold() == null) {
            updatedLoan.setHousehold(householdRepository.findById(householdId).get());
        }

        loanRepository.save(updatedLoan);
    }

    public ReportIntDTO countLoansBalance(Integer householdId) {
        ReportIntDTO report = new ReportIntDTO();
        BigDecimal burdenTmp = BigDecimal.valueOf(0);

        List<Loan> loansList = aggregateLoans(householdId);

        if (loansList != null && !loansList.isEmpty()) {
            for (Loan loan : loansList) {
                burdenTmp = burdenTmp.add(loan.getInstallmentAmount());
            }
        }

        report.setBurden(burdenTmp);
        return report;
    }

    private List<Loan> aggregateLoans(Integer householdId) {
        Optional<List<Loan>> optList = Optional.of(loanRepository.findAllByHousehold_Id(householdId).orElse(Collections.emptyList()));

        return optList.stream()
                .flatMap(Collection::stream)
                .filter(loan -> YearMonth.now().atEndOfMonth().isBefore(loan.getEndDate()))
                .collect(Collectors.toList());
    }
}
