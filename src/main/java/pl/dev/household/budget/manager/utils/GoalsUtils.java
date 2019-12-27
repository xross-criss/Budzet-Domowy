package pl.dev.household.budget.manager.utils;

import pl.dev.household.budget.manager.dao.GoalsDAO;
import pl.dev.household.budget.manager.domain.Goals;

import java.util.Optional;

public class GoalsUtils {

    public static Goals convertToDTO(Optional<GoalsDAO> goalsDAO) {
        Goals goals = new Goals();

        if (goalsDAO.isEmpty()) {
            throw new RuntimeException("No goal");
        }

        goals.setId(goalsDAO.get().getId());
        goals.setHousehold(HouseholdUtils.convertToDTO(Optional.ofNullable(goalsDAO.get().getHousehold())));
        goals.setCategory(goalsDAO.get().getCategory());
        goals.setAmount(goalsDAO.get().getAmount());
        goals.setName(goalsDAO.get().getName());
        goals.setDescription(goalsDAO.get().getDescription());
        goals.setPriority(goalsDAO.get().getPriority());

        return goals;
    }

    public static GoalsDAO convertToDAO(Goals goals) {
        GoalsDAO goalsDAO = new GoalsDAO();

        goalsDAO.setId(goals.getId());
        goalsDAO.setHousehold(HouseholdUtils.convertToDAO(goals.getHousehold()));
        goalsDAO.setCategory(goals.getCategory());
        goalsDAO.setAmount(goals.getAmount());
        goalsDAO.setName(goals.getName());
        goalsDAO.setDescription(goals.getDescription());
        goalsDAO.setPriority(goals.getPriority());

        return goalsDAO;
    }

}
