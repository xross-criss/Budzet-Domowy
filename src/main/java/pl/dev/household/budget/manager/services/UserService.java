package pl.dev.household.budget.manager.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.dev.household.budget.manager.dao.UserDAO;
import pl.dev.household.budget.manager.dao.repository.UserRepository;
import pl.dev.household.budget.manager.domain.User;
import pl.dev.household.budget.manager.utils.UserUtils;

import java.util.Optional;

@Slf4j
@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUser(Integer userId) {
        return UserUtils.convertToDTO(userRepository.findById(userId));
    }

    public User updateUser(Integer userId, User user) {
        Optional<UserDAO> oldUser = userRepository.findById(userId);

        if (oldUser.isEmpty() || !oldUser.get().getId().equals(user.getId())) {
            throw new RuntimeException("User cannot be updated!");
        }

        UserDAO updatedUser = UserUtils.convertToDAO(user);
        userRepository.save(updatedUser);

        return getUser(userId);
    }

    public User registerUser(User user) {
        UserDAO userDAO = UserUtils.convertToDAO(user);
        userRepository.save(userDAO);

        return getUser(userDAO.getId());
    }
}
