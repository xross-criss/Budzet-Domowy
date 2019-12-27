package pl.dev.household.budget.manager.utils;

import pl.dev.household.budget.manager.dao.InsuranceDAO;
import pl.dev.household.budget.manager.domain.Insurance;

import java.util.Optional;

public class InsuranceUtils {

    public static Insurance convertToDTO(Optional<InsuranceDAO> insuranceDAO) {
        Insurance insurance = new Insurance();

        if (insuranceDAO.isEmpty()) {
            throw new RuntimeException("No insurance");
        }

        insurance.setId(insuranceDAO.get().getId());
        insurance.setHousehold((HouseholdUtils.convertToDTO(Optional.ofNullable(insuranceDAO.get().getHousehold()))));
        insurance.setType(insuranceDAO.get().getType());
        insurance.setDescription(insuranceDAO.get().getDescription());
        insurance.setInterval(insuranceDAO.get().getInterval());
        insurance.setCost(insuranceDAO.get().getCost());
        insurance.setEndDate(insuranceDAO.get().getEndDate());
        insurance.setName(insuranceDAO.get().getName());
        insurance.setVehicleTID(insuranceDAO.get().getVehicleTID());
        insurance.setVehicleLP(insuranceDAO.get().getVehicleLP());

        return insurance;
    }

    public static InsuranceDAO convertToDAO(Insurance insurance) {
        InsuranceDAO insuranceDAO = new InsuranceDAO();

        insuranceDAO.setId(insurance.getId());
        insuranceDAO.setHousehold(HouseholdUtils.convertToDAO(insurance.getHousehold()));
        insuranceDAO.setType(insurance.getType());
        insuranceDAO.setDescription(insurance.getDescription());
        insuranceDAO.setInterval(insurance.getInterval());
        insuranceDAO.setCost(insurance.getCost());
        insuranceDAO.setEndDate(insurance.getEndDate());
        insuranceDAO.setVehicleTID(insurance.getVehicleTID());
        insuranceDAO.setName(insurance.getName());
        insuranceDAO.setVehicleLP(insurance.getVehicleLP());

        return insuranceDAO;
    }

}
