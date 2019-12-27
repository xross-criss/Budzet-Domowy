package pl.dev.household.budget.manager.domain;

import lombok.*;
import pl.dev.household.budget.manager.dictionaries.UserRole;

import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private Integer id;
    private Household household;
    private UserRole userRole;
    private String login;
    private String password;
    private String name;
    private String email;
    private Date registrationDate;
    private Date lastFailedLogin;

}