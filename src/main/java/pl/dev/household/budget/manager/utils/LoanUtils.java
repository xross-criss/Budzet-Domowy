package pl.dev.household.budget.manager.utils;


import pl.dev.household.budget.manager.dao.LoanDAO;
import pl.dev.household.budget.manager.domain.Loan;

import java.util.Optional;

public class LoanUtils {

    public static Loan convertToDTO(Optional<LoanDAO> loanDAO) {
        Loan loan = new Loan();

        if (loanDAO.isEmpty()) {
            throw new RuntimeException("No cashflow");
        }

        loan.setId(loanDAO.get().getId());
        loan.setHousehold(HouseholdUtils.convertToDTO(Optional.ofNullable(loanDAO.get().getHousehold())));
        loan.setBankName(loanDAO.get().getBankName());
        loan.setAnnualLoanPercentage(loanDAO.get().getAnnualLoanPercentage());
        loan.setStartDate(loanDAO.get().getStartDate());
        loan.setEndDate(loanDAO.get().getEndDate());
        loan.setAmount(loanDAO.get().getAmount());
        loan.setInstallmentAmount(loanDAO.get().getInstallmentAmount());

        return loan;
    }

    public static LoanDAO convertToDAO(Loan loan) {
        LoanDAO loanDAO = new LoanDAO();

        loanDAO.setId(loan.getId());
        loanDAO.setHousehold(HouseholdUtils.convertToDAO(loan.getHousehold()));
        loanDAO.setBankName(loan.getBankName());
        loanDAO.setAnnualLoanPercentage(loan.getAnnualLoanPercentage());
        loanDAO.setStartDate(loan.getStartDate());
        loanDAO.setEndDate(loan.getEndDate());
        loanDAO.setAmount(loan.getAmount());
        loanDAO.setInstallmentAmount(loan.getInstallmentAmount());

        return loanDAO;
    }

}
