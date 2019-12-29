package pl.dev.household.budget.manager.services;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.dev.household.budget.manager.dao.LoanDAO;
import pl.dev.household.budget.manager.dao.repository.LoanRepository;
import pl.dev.household.budget.manager.domain.Loan;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class LoanService {

    private ModelMapper modelMapper;
    private LoanRepository loanRepository;

    @Autowired
    public LoanService(LoanRepository loanRepository) {
        this.loanRepository = loanRepository;
    }

    public List<Loan> getLoans(Integer householdId) {
        return loanRepository.findAllByHousehold_Id(householdId).stream()
                .map(loan -> modelMapper.map(loan, Loan.class))
                .collect(Collectors.toList());
    }

    public Loan getLoan(Integer loanId) {
        return modelMapper.map(loanRepository.findById(loanId), Loan.class);
    }

    public Loan addLoan(Loan loan) {
        Integer loanId = loanRepository.save(modelMapper.map(loan, LoanDAO.class)).getId();
        return getLoan(loanId);
    }

    public Loan updateLoan(Integer householdId, Loan loan) {
        Optional<LoanDAO> oldLoan = loanRepository.findById(loan.getId());
        if (oldLoan.isEmpty() || !oldLoan.get().getId().equals(loan.getId())) {
            throw new RuntimeException("Loan cannot be updated!");
        }

        LoanDAO updatedLoan = modelMapper.map(loan, LoanDAO.class);
        loanRepository.save(updatedLoan);

        return getLoan(updatedLoan.getId());
    }
}
