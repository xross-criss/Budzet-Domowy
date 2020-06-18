package pl.dev.household.budget.manager.services;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import pl.dev.household.budget.manager.dao.Loan;
import pl.dev.household.budget.manager.dao.repository.LoanRepository;
import pl.dev.household.budget.manager.domain.LoanDTO;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class LoanService {

    private ModelMapper modelMapper;
    private LoanRepository loanRepository;

    public LoanService(ModelMapper modelMapper, LoanRepository loanRepository) {
        this.modelMapper = modelMapper;
        this.loanRepository = loanRepository;
    }

    public List<LoanDTO> getLoans(Integer householdId) {
        return loanRepository.findAllByHousehold_Id(householdId).stream()
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

    public LoanDTO updateLoan(Integer householdId, LoanDTO loanDTO) {
        Optional<Loan> oldLoan = loanRepository.findById(loanDTO.getId());
        if (oldLoan.isEmpty() || !oldLoan.get().getId().equals(loanDTO.getId())) {
            throw new RuntimeException("Loan cannot be updated!");
        }

        Loan updatedLoan = modelMapper.map(loanDTO, Loan.class);
        loanRepository.save(updatedLoan);

        return getLoan(updatedLoan.getId());
    }
}
