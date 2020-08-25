package pl.dev.household.budget.manager.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.dev.household.budget.manager.domain.UserDTO;
import pl.dev.household.budget.manager.security.util.Security;
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

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/details")
    public ResponseEntity<UserDTO> getUser() {
        return ResponseEntity.ok(userService.getUser(Security.currentUser().getId()));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/household")
    public ResponseEntity<List<UserDTO>> getAllUsersForHousehold() {
        return ResponseEntity.ok(userService.getAllUsersForHousehold(Security.currentUser().getHousehold().getId()));
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> registerUser(@RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(userService.registerUser(userDTO));
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/update")
    public void updateUser(@RequestBody UserDTO userDTO) {
        userService.updateUser(Security.currentUser().getId(), userDTO);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/addUser")
    public void addUserToHousehold(@RequestParam("login") String login) {
        userService.addUserToHousehold(Security.currentUser().getHousehold().getId(), login);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/removeUser")
    public void removeUserFromHousehold(@RequestParam("login") String login) {
        userService.removeUserFromHousehold(Security.currentUser().getHousehold().getId(), login);
    }

}
