package pl.dev.household.budget.manager.services;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;
import pl.dev.household.budget.manager.dao.Loan;
import pl.dev.household.budget.manager.dao.User;
import pl.dev.household.budget.manager.dao.repository.LoanRepository;
import pl.dev.household.budget.manager.domain.LoanDTO;
import pl.dev.household.budget.manager.domain.ReportIntDTO;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class LoanService {

    private ModelMapper modelMapper;
    private LoanRepository loanRepository;
    private UserService userService;

    public LoanService(ModelMapper modelMapper, LoanRepository loanRepository, UserService userService) {
        this.modelMapper = modelMapper;
        this.loanRepository = loanRepository;
        this.userService = userService;
    }

    public List<LoanDTO> getLoans(Integer householdId) throws Exception {
        return getLoansForHouseholdUsers(householdId);
    }

    public LoanDTO getLoan(Integer loanId) {
        return modelMapper.map(loanRepository.findById(loanId), LoanDTO.class);
    }

    public LoanDTO addLoan(LoanDTO loanDTO) {
        Integer loanId = loanRepository.save(modelMapper.map(loanDTO, Loan.class)).getId();
        return getLoan(loanId);
    }

    public void updateLoan(Integer userId, LoanDTO loanDTO) {
        Loan updatedLoan = modelMapper.map(loanDTO, Loan.class);

        if (updatedLoan.getUser() == null) {
            updatedLoan.setUser(modelMapper.map(userService.getUser(userId), User.class));
        }

        loanRepository.save(updatedLoan);
    }

    public ReportIntDTO countLoansBalance(Integer householdId) throws Exception {
        ReportIntDTO report = new ReportIntDTO();
        BigDecimal burdenTmp = BigDecimal.valueOf(0);

        List<LoanDTO> loansList = aggregateLoans(householdId);

        if (loansList != null && !loansList.isEmpty()) {
            for (LoanDTO loan : loansList) {
                burdenTmp = burdenTmp.add(loan.getInstallmentAmount());
            }
        }

        report.setBurden(burdenTmp);
        return report;
    }

    private List<LoanDTO> getLoansForHouseholdUsers(Integer householdId) throws Exception {
        List<User> usersInHousehold = userService.getAllUsersInHouseholdByHousehold(householdId);

        if (usersInHousehold.isEmpty()) {
            throw new Exception("No users in household found!");
        }

        List<Loan> loanList = new ArrayList<>();

        usersInHousehold.forEach(user -> {
            loanList.addAll(loanRepository.findAllByUserId(user.getId()).orElse(Collections.emptyList()));
        });

        return modelMapper.map(loanList, new TypeToken<List<LoanDTO>>() {
        }.getType());
    }

    private List<LoanDTO> aggregateLoans(Integer householdId) throws Exception {
        return getLoansForHouseholdUsers(householdId).stream()
                .filter(loan -> YearMonth.now().atEndOfMonth().isBefore(loan.getEndDate()))
                .collect(Collectors.toList());
    }
}
