package pl.dev.household.budget.manager.utils;

import pl.dev.household.budget.manager.dao.Household;
import pl.dev.household.budget.manager.domain.HouseholdDTO;

public class HouseholdMapper {

    public static HouseholdDTO mapHousehold(Household household) {
        HouseholdDTO householdDTO = new HouseholdDTO();

        householdDTO.setId(household.getId());
        householdDTO.setPopulation(household.getPopulation());
        householdDTO.setCost(household.getCost());

        return householdDTO;
    }

}
