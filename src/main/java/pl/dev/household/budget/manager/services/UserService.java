package pl.dev.household.budget.manager.services;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import pl.dev.household.budget.manager.dao.Household;
import pl.dev.household.budget.manager.dao.User;
import pl.dev.household.budget.manager.dao.repository.HouseholdRepository;
import pl.dev.household.budget.manager.dao.repository.UserRepository;
import pl.dev.household.budget.manager.domain.UserDTO;
import pl.dev.household.budget.manager.security.util.Security;
import pl.dev.household.budget.manager.utils.UserMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserService {

    private ModelMapper modelMapper;
    private UserRepository userRepository;
    private HouseholdRepository householdRepository;

    public UserService(ModelMapper modelMapper, UserRepository userRepository, HouseholdRepository householdRepository) {
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.householdRepository = householdRepository;
    }

    public UserDTO getUser(Integer userId) {
        return UserMapper.mapUser(userRepository.findById(userId).get());
    }

    public void updateUser(Integer userId, UserDTO userDTO) {
        User updatedUser = modelMapper.map(userDTO, User.class);

        if (updatedUser.getHousehold() == null) {
            updatedUser.setHousehold(householdRepository.findById(Security.currentUser().getHousehold().getId()).get());
        }

        userRepository.save(updatedUser);
    }

    public UserDTO registerUser(UserDTO userDTO) {
        User user = modelMapper.map(userDTO, User.class);
        userRepository.save(user);

        return getUser(user.getId());
    }

    public List<UserDTO> getAllUsersForHousehold(Integer householdId) {
        return userRepository.findAllByHousehold_Id(householdId).stream().map(user -> modelMapper.map(user, UserDTO.class)).peek(userDTO -> userDTO.setPassword("*******")).collect(Collectors.toList());
    }

    public void addUserToHousehold(Integer householdId, String login) {
        User dbUser = userRepository.findOneByLogin(login);

        if (dbUser.getHousehold() == null) {
            Optional<Household> household = Optional.of(householdRepository.findById(householdId)).orElse(null);
            household.ifPresent(dbUser::setHousehold);
            userRepository.save(dbUser);
        }
    }

    public void removeUserFromHousehold(Integer householdId, String login) {
        User dbUser = userRepository.findOneByLogin(login);

        if (dbUser.getHousehold() != null && dbUser.getHousehold().getId().equals(householdId)) {
            dbUser.setHousehold(null);
            userRepository.save(dbUser);
        }
    }
}
