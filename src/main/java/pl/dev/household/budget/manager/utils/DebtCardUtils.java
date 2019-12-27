package pl.dev.household.budget.manager.utils;


import pl.dev.household.budget.manager.dao.DebtCardDAO;
import pl.dev.household.budget.manager.domain.DebtCard;

import java.util.Optional;

public class DebtCardUtils {

    public static DebtCard convertToDTO(Optional<DebtCardDAO> debtCardDAO) {
        DebtCard debtCard = new DebtCard();

        if (debtCardDAO.isEmpty()) {
            throw new RuntimeException("No cashflow");
        }

        debtCard.setId(debtCardDAO.get().getId());
        debtCard.setHousehold(HouseholdUtils.convertToDTO(Optional.ofNullable(debtCardDAO.get().getHousehold())));
        debtCard.setLimit(debtCardDAO.get().getLimit());
        debtCard.setBalance(debtCardDAO.get().getBalance());
        debtCard.setRenewalPercentage(debtCardDAO.get().getRenewalPercentage());
        debtCard.setAnnualPercentage(debtCardDAO.get().getAnnualPercentage());
        debtCard.setBank(debtCardDAO.get().getBank());
        debtCard.setName(debtCardDAO.get().getName());

        return debtCard;
    }

    public static DebtCardDAO convertToDAO(DebtCard debtCard) {
        DebtCardDAO debtCardDAO = new DebtCardDAO();

        debtCardDAO.setId(debtCard.getId());
        debtCardDAO.setHousehold(HouseholdUtils.convertToDAO(debtCard.getHousehold()));
        debtCardDAO.setLimit(debtCard.getLimit());
        debtCardDAO.setBalance(debtCard.getBalance());
        debtCardDAO.setRenewalPercentage(debtCard.getRenewalPercentage());
        debtCardDAO.setAnnualPercentage(debtCard.getAnnualPercentage());
        debtCardDAO.setBank(debtCard.getBank());
        debtCardDAO.setName(debtCard.getName());

        return debtCardDAO;
    }

}
