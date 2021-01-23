package pl.dev.household.budget.manager.services;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.modelmapper.ModelMapper;
import org.postgresql.util.MD5Digest;
import org.springframework.stereotype.Service;
import pl.dev.household.budget.manager.dao.Household;
import pl.dev.household.budget.manager.dao.User;
import pl.dev.household.budget.manager.dao.UserHist;
import pl.dev.household.budget.manager.dao.repository.HouseholdRepository;
import pl.dev.household.budget.manager.dao.repository.UserHistRepository;
import pl.dev.household.budget.manager.dao.repository.UserRepository;
import pl.dev.household.budget.manager.domain.*;
import pl.dev.household.budget.manager.security.exception.PasswordException;
import pl.dev.household.budget.manager.security.util.Security;
import pl.dev.household.budget.manager.utils.UserMapper;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserService {

    private ModelMapper modelMapper;
    private UserRepository userRepository;
    private HouseholdRepository householdRepository;
    private UserHistRepository userHistRepository;
    private CategoryService categoryService;
    private BankService bankService;

    public UserService(
            ModelMapper modelMapper,
            UserRepository userRepository,
            HouseholdRepository householdRepository,
            UserHistRepository userHistRepository,
            CategoryService categoryService,
            BankService bankService
    ) {
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.householdRepository = householdRepository;
        this.userHistRepository = userHistRepository;
        this.categoryService = categoryService;
        this.bankService = bankService;
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
        List<User> optList = userRepository.findAllByHousehold_Id(householdId).orElse(Collections.emptyList());

        return optList.stream().map(user -> modelMapper.map(user, UserDTO.class)).peek(userDTO -> userDTO.setPassword("********")).collect(Collectors.toList());
    }

    public void addUserToHousehold(Integer householdId, String login) throws Exception {
        User dbUser = userRepository.findOneByLogin(login).orElse(null);

        if (dbUser == null) {
            throw new Exception("User not found!");
        }

        assignHouseholdToUser(householdId, dbUser);
    }

    public void addUserToHousehold(Integer householdId, Integer userId) throws Exception {
        User dbUser = userRepository.findById(userId).orElse(null);

        if (dbUser == null) {
            throw new Exception("User not found!");
        }

        assignHouseholdToUser(householdId, dbUser);
    }

    private void assignHouseholdToUser(Integer householdId, User dbUser) throws Exception {
        if (dbUser.getHousehold() == null) {
            Household household = householdRepository.findById(householdId).orElse(null);

            if (household == null) {
                throw new Exception("Household not found!");
            }

            dbUser.setHousehold(household);
            userRepository.save(dbUser);

            reassignUserData(dbUser.getId(), householdId);
        }
    }

    private void reassignUserData(Integer userId, Integer householdId) throws Exception {
        List<UserHist> histList = userHistRepository.findAllByUserIdAndOperationLike(userId, "UPDATE").orElse(Collections.emptyList());

        if (histList.isEmpty()) {
            throw new Exception("No history found!");
        }

        UserHist userHist = histList.stream()
                .filter(checkForUserHistConditions())
                .sorted(Comparator.comparing(UserHist::getOperationDate))
                .collect(Collectors.toList())
                .get(0);

        List<CategoryDTO> categoryList = categoryService.getCategoryList(userHist.getHouseholdOld().getId());
        List<BankDTO> bankList = bankService.getBankList(userHist.getHouseholdOld().getId());

        Household household = householdRepository.findById(householdId).orElse(null);

        if (household == null) {
            throw new Exception("household not found!");
        }

        HouseholdDTO householdDTO = modelMapper.map(household, HouseholdDTO.class);

        categoryList.forEach(categoryDTO -> {
            categoryDTO.setHousehold(householdDTO);
            categoryService.saveCategory(categoryDTO);
        });

        bankList.forEach(bankDTO -> {
            bankDTO.setHousehold(householdDTO);
            bankService.saveBank(bankDTO);
        });

    }

    private Predicate<? super UserHist> checkForUserHistConditions() {
        return userHist -> (Objects.nonNull(userHist.getHouseholdOld()) && (Objects.isNull(userHist.getHousehold())));
    }

    public void removeUserFromHousehold(Integer householdId, String login) throws Exception {
        User dbUser = userRepository.findOneByLogin(login).orElse(null);

        if (dbUser == null) {
            throw new Exception("User not found!");
        }

        if (dbUser.getHousehold() != null && dbUser.getHousehold().getId().equals(householdId)) {
            dbUser.setHousehold(null);
            userRepository.save(dbUser);
        }
    }

    public List<User> getAllUsersInHouseholdByHousehold(Integer householdId) {
        return userRepository.findAllByHousehold_Id(householdId).orElse(Collections.emptyList());
    }

    public UserDTO getUserDetails(Integer userId) {
        UserDTO userDTO = getUser(userId);
        userDTO.setPassword("********");

        return userDTO;
    }

    public void changePassword(User currentUser, PasswordDTO passwordDTO) {
        String oldPasswordMd5 = DigestUtils.md5Hex(passwordDTO.getOldPassword());
        String newPasswordMd5 = DigestUtils.md5Hex(passwordDTO.getNewPassword());

        if (!currentUser.getPassword().equals(oldPasswordMd5)) {
            throw new PasswordException("Old password is incorrect. Cannot change password!");

        }
        currentUser.setPassword(newPasswordMd5);
        userRepository.save(currentUser);
    }

    public List<UserDTO> hidUserPassword(List<UserDTO> userDTOList){
        return userDTOList.stream().map(this::hidUserPassword).collect(Collectors.toList());
    }

    public UserDTO hidUserPassword(UserDTO userDTO){
        userDTO.setPassword("********");
        return userDTO;
    }

//    public <T> List<T> hideUserPassword(List<T> dtoList){
//        return dtoList.stream().map(t -> t.)
//    }
}
