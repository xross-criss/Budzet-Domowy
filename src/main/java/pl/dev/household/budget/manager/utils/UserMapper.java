package pl.dev.household.budget.manager.utils;

import pl.dev.household.budget.manager.dao.User;
import pl.dev.household.budget.manager.domain.UserDTO;

public class UserMapper {

    public static UserDTO mapUser(User user) {
        UserDTO userDTO = new UserDTO();

        userDTO.setId(user.getId());
        userDTO.setHousehold(HouseholdMapper.mapHousehold(user.getHousehold()));
        userDTO.setUserRole(user.getUserRole());
        userDTO.setLogin(user.getLogin());
        userDTO.setPassword(user.getPassword());
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
        userDTO.setRegistrationDate(user.getRegistrationDate());
        userDTO.setLastFailedLogin(user.getLastFailedLogin());

        return userDTO;
    }

}
