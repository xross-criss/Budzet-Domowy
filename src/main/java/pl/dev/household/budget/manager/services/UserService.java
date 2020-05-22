package pl.dev.household.budget.manager.services;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.dev.household.budget.manager.dao.UserDAO;
import pl.dev.household.budget.manager.dao.repository.UserRepository;
import pl.dev.household.budget.manager.domain.User;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserService {

    private ModelMapper modelMapper;
    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUser(Integer userId) {
        return modelMapper.map(userRepository.findById(userId), User.class);
    }

    public User updateUser(Integer userId, User user) {
        Optional<UserDAO> oldUser = userRepository.findById(userId);

        if (oldUser.isEmpty() || !oldUser.get().getId().equals(user.getId())) {
            throw new RuntimeException("User cannot be updated!");
        }

        UserDAO updatedUser = modelMapper.map(user, UserDAO.class);
        userRepository.save(updatedUser);

        return getUser(userId);
    }

    public User registerUser(User user) {
        UserDAO userDAO = modelMapper.map(user, UserDAO.class);
        userRepository.save(userDAO);

        return getUser(userDAO.getId());
    }

    public List<User> getAllUsersForHousehold(Integer householdId) {
        return userRepository.findAllByHousehold_Id(householdId).stream().map(user -> modelMapper.map(user, User.class)).peek(user -> user.setPassword("*******")).collect(Collectors.toList());
    }
}
