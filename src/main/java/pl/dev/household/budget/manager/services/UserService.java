package pl.dev.household.budget.manager.services;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import pl.dev.household.budget.manager.dao.User;
import pl.dev.household.budget.manager.dao.repository.UserRepository;
import pl.dev.household.budget.manager.domain.UserDTO;
import pl.dev.household.budget.manager.utils.UserMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserService {

    private ModelMapper modelMapper;
    private UserRepository userRepository;

    public UserService(ModelMapper modelMapper, UserRepository userRepository) {
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
    }

    public UserDTO getUser(Integer userId) {
        return UserMapper.mapUser(userRepository.findById(userId).get());
    }

    public UserDTO updateUser(Integer userId, UserDTO userDTO) {
        Optional<User> oldUser = userRepository.findById(userId);

        if (oldUser.isEmpty() || !oldUser.get().getId().equals(userDTO.getId())) {
            throw new RuntimeException("User cannot be updated!");
        }

        User updatedUser = modelMapper.map(userDTO, User.class);
        userRepository.save(updatedUser);

        return getUser(userId);
    }

    public UserDTO registerUser(UserDTO userDTO) {
        User user = modelMapper.map(userDTO, User.class);
        userRepository.save(user);

        return getUser(user.getId());
    }

    public List<UserDTO> getAllUsersForHousehold(Integer householdId) {
        return userRepository.findAllByHousehold_Id(householdId).stream().map(user -> modelMapper.map(user, UserDTO.class)).peek(userDTO -> userDTO.setPassword("*******")).collect(Collectors.toList());
    }
}
