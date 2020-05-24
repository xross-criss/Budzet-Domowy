package pl.dev.household.budget.manager.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pl.dev.household.budget.manager.domain.UserDTO;
import pl.dev.household.budget.manager.services.UserService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/user")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/details/{userId}")
    public UserDTO getUser(@RequestParam("userId") Integer userId) {
        return userService.getUser(userId);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/household/{householdId}")
    public List<UserDTO> getAllUsersForHousehold(@RequestParam("userId") Integer householdId) {
        return userService.getAllUsersForHousehold(householdId);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public UserDTO registerUser(@RequestBody UserDTO userDTO) {
        return userService.registerUser(userDTO);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/details/{userId}")
    public UserDTO updateUser(@RequestParam("userId") Integer userId, @RequestBody UserDTO userDTO) {
        return userService.updateUser(userId, userDTO);
    }


}
