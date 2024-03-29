package pl.dev.household.budget.manager.domain;

import lombok.*;
import pl.dev.household.budget.manager.dictionaries.UserRole;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private Integer id;
    private HouseholdDTO household;
    private UserRole userRole;
    private String login;
    private String password;
    private String name;
    private String email;
    private LocalDate registrationDate;
    private LocalDate lastFailedLogin;

}
