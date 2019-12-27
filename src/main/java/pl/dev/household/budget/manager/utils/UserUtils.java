package pl.dev.household.budget.manager.utils;

import pl.dev.household.budget.manager.dao.HouseholdDAO;
import pl.dev.household.budget.manager.dao.UserDAO;
import pl.dev.household.budget.manager.domain.Household;
import pl.dev.household.budget.manager.domain.User;

import java.util.Optional;

public class UserUtils {

    public static User convertToDTO(Optional<UserDAO> userDAO) {
        User user = new User();

        if (userDAO.isEmpty()) {
            throw new RuntimeException("No user");
        }

        user.setId(userDAO.get().getId());
        user.setHousehold(HouseholdUtils.convertToDTO(Optional.ofNullable(userDAO.get().getHousehold())));
        user.setUserRole(userDAO.get().getRole());
        user.setLogin(userDAO.get().getLogin());
        user.setPassword(userDAO.get().getPassword());
        user.setName(userDAO.get().getName());
        user.setEmail(userDAO.get().getEmail());
        user.setRegistrationDate(userDAO.get().getRegistrationDate());
        user.setLastFailedLogin(userDAO.get().getLastFailedLogin());

        return user;
    }

    public static UserDAO convertToDAO(User user) {
        UserDAO userDAO = new UserDAO();

        userDAO.setId(user.getId());
        userDAO.setHousehold(HouseholdUtils.convertToDAO(user.getHousehold()));
        userDAO.setRole(user.getUserRole());
        userDAO.setLogin(user.getLogin());
        userDAO.setPassword(user.getPassword());
        userDAO.setName(user.getName());
        userDAO.setEmail(user.getEmail());
        userDAO.setRegistrationDate(user.getRegistrationDate());
        userDAO.setLastFailedLogin(user.getLastFailedLogin());

        return userDAO;
    }
}
