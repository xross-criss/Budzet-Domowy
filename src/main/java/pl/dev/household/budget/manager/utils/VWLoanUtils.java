package pl.dev.household.budget.manager.utils;


import pl.dev.household.budget.manager.dao.LoanDAO;
import pl.dev.household.budget.manager.dao.view.VWLoanDAO;
import pl.dev.household.budget.manager.domain.view.VWLoan;

import java.util.Optional;

public class VWLoanUtils {

    public static VWLoan convertToDTO(Optional<VWLoanDAO> vwLoanDAO) {
        VWLoan vWLoan = new VWLoan();

        if (vwLoanDAO.isEmpty()) {
            throw new RuntimeException("No Loan in view");
        }

        vWLoan.setId(vwLoanDAO.get().getId());
        vWLoan.setHousehold(HouseholdUtils.convertToDTO(Optional.ofNullable(vwLoanDAO.get().getHousehold())));
        vWLoan.setBankName(vwLoanDAO.get().getBankName());
        vWLoan.setAnnualLoanPercentage(vwLoanDAO.get().getAnnualLoanPercentage());
        vWLoan.setStartDate(vwLoanDAO.get().getStartDate());
        vWLoan.setEndDate(vwLoanDAO.get().getEndDate());
        vWLoan.setAmount(vwLoanDAO.get().getAmount());
        vWLoan.setFullInstallmentDuration(vwLoanDAO.get().getFullInstallmentDuration());
        vWLoan.setInstallmentDurationToGo(vwLoanDAO.get().getInstallmentDurationToGo());
        vWLoan.setInstallmentAmount(vwLoanDAO.get().getInstallmentAmount());

        return vWLoan;
    }

    public static VWLoanDAO convertToDAO(VWLoan vwLoan) {
        VWLoanDAO vWLoanDAO = new VWLoanDAO();

        vWLoanDAO.setId(vwLoan.getId());
        vWLoanDAO.setHousehold(HouseholdUtils.convertToDAO(vwLoan.getHousehold()));
        vWLoanDAO.setBankName(vwLoan.getBankName());
        vWLoanDAO.setAnnualLoanPercentage(vwLoan.getAnnualLoanPercentage());
        vWLoanDAO.setStartDate(vwLoan.getStartDate());
        vWLoanDAO.setEndDate(vwLoan.getEndDate());
        vWLoanDAO.setAmount(vwLoan.getAmount());
        vWLoanDAO.setFullInstallmentDuration(vwLoan.getFullInstallmentDuration());
        vWLoanDAO.setInstallmentDurationToGo(vwLoan.getInstallmentDurationToGo());
        vWLoanDAO.setInstallmentAmount(vwLoan.getInstallmentAmount());

        return vWLoanDAO;
    }

    public static LoanDAO convertToLoanDAO(VWLoan vwLoan) {
        LoanDAO loanDAO = new LoanDAO();

        loanDAO.setId(vwLoan.getId());
        loanDAO.setHousehold(HouseholdUtils.convertToDAO(vwLoan.getHousehold()));
        loanDAO.setBankName(vwLoan.getBankName());
        loanDAO.setAnnualLoanPercentage(vwLoan.getAnnualLoanPercentage());
        loanDAO.setStartDate(vwLoan.getStartDate());
        loanDAO.setEndDate(vwLoan.getEndDate());
        loanDAO.setAmount(vwLoan.getAmount());
        loanDAO.setInstallmentAmount(vwLoan.getInstallmentAmount());

        return loanDAO;
    }

}
