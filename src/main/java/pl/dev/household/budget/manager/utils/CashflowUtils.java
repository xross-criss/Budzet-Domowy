package pl.dev.household.budget.manager.utils;

import pl.dev.household.budget.manager.dao.CashflowDAO;
import pl.dev.household.budget.manager.domain.Cashflow;

import java.util.Optional;

public class CashflowUtils {

    public static Cashflow convertToDTO(Optional<CashflowDAO> cashflowDAO) {
        Cashflow cashflow = new Cashflow();

        if (cashflowDAO.isEmpty()) {
            throw new RuntimeException("No cashflow");
        }

        cashflow.setId(cashflowDAO.get().getId());
        cashflow.setHousehold(HouseholdUtils.convertToDTO(Optional.ofNullable(cashflowDAO.get().getHousehold())));
        cashflow.setCategory(cashflowDAO.get().getCategory());
        cashflow.setStartDate(cashflowDAO.get().getStartDate());
        cashflow.setEndDate(cashflowDAO.get().getEndDate());
        cashflow.setInterval(cashflowDAO.get().getInterval());
        cashflow.setAmount(cashflowDAO.get().getAmount());
        cashflow.setDescription(cashflowDAO.get().getDescription());

        return cashflow;
    }

    public static CashflowDAO convertToDAO(Cashflow cashflow) {
        CashflowDAO cashflowDAO = new CashflowDAO();

        cashflowDAO.setId(cashflow.getId());
        cashflowDAO.setHousehold(HouseholdUtils.convertToDAO(cashflow.getHousehold()));
        cashflowDAO.setCategory(cashflow.getCategory());
        cashflowDAO.setStartDate(cashflow.getStartDate());
        cashflowDAO.setEndDate(cashflow.getEndDate());
        cashflowDAO.setInterval(cashflow.getInterval());
        cashflowDAO.setAmount(cashflow.getAmount());
        cashflowDAO.setDescription(cashflow.getDescription());

        return cashflowDAO;
    }
}
