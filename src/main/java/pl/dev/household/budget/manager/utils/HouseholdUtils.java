package pl.dev.household.budget.manager.utils;

import pl.dev.household.budget.manager.dao.HouseholdDAO;
import pl.dev.household.budget.manager.domain.Household;

import java.util.Optional;

public class HouseholdUtils {

    public static Household convertToDTO(Optional<HouseholdDAO> householdDAO) {

        if (householdDAO.isEmpty()) {
            throw new RuntimeException("No household");
        }

        Household household = new Household();
        household.setId(householdDAO.get().getId());
        household.setPopulation(householdDAO.get().getPopulation());
        household.setCost(householdDAO.get().getCost());

        return household;
    }

    public static HouseholdDAO convertToDAO(Household household) {
        HouseholdDAO householdDAO = new HouseholdDAO();

        householdDAO.setId(household.getId());
        householdDAO.setPopulation(household.getPopulation());
        householdDAO.setCost(household.getCost());

        return householdDAO;
    }
}
