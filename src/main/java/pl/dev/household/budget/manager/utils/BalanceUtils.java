package pl.dev.household.budget.manager.utils;

import pl.dev.household.budget.manager.dao.BalanceDAO;
import pl.dev.household.budget.manager.domain.Balance;

import java.util.Optional;

public class BalanceUtils {

    public static Balance convertToDTO(Optional<BalanceDAO> balanceDAO) {
        Balance balance = new Balance();

        if (balanceDAO.isEmpty()) {
            throw new RuntimeException("No balance");
        }

        balance.setId(balanceDAO.get().getId());
        balance.setHousehold(HouseholdUtils.convertToDTO(Optional.ofNullable(balanceDAO.get().getHousehold())));
        balance.setType(balanceDAO.get().getType());
        balance.setGenerationDate(balanceDAO.get().getGenerationDate());
        balance.setBurden(balanceDAO.get().getBurden());
        balance.setIncome(balanceDAO.get().getIncome());
        balance.setBalance(balanceDAO.get().getBalance());

        return balance;
    }

    public static BalanceDAO convertToDAO(Balance balance) {
        BalanceDAO balanceDAO = new BalanceDAO();

        balanceDAO.setId(balance.getId());
        balanceDAO.setHousehold(HouseholdUtils.convertToDAO(balance.getHousehold()));
        balanceDAO.setType(balance.getType());
        balanceDAO.setGenerationDate(balance.getGenerationDate());
        balanceDAO.setBurden(balance.getBurden());
        balanceDAO.setIncome(balance.getIncome());
        balanceDAO.setBalance(balance.getBalance());

        return balanceDAO;
    }
}
