package pl.dev.household.budget.manager.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pl.dev.household.budget.manager.domain.Household;
import pl.dev.household.budget.manager.domain.User;
import pl.dev.household.budget.manager.services.UserService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/user")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/{userId}")
    public User getUser(@RequestParam("userId") Integer userId) {
        return userService.getUser(userId);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/household/{householdId}")
    public List<User> getAllUsersForHousehold(@RequestParam("userId") Integer householdId) {
        return userService.getAllUsersForHousehold(householdId);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public User registerUser(@RequestBody User user) {
        return userService.registerUser(user);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/{userId}")
    public User updateUser(@RequestParam("userId") Integer userId, @RequestBody User user) {
        return userService.updateUser(userId, user);
    }

/*    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/login")
    public User updateUser(@RequestBody User user) {
        return userService.login(user);
    }*/

}
