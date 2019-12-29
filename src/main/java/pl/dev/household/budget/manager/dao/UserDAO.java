package pl.dev.household.budget.manager.dao;

import lombok.Getter;
import lombok.Setter;
import pl.dev.household.budget.manager.dictionaries.UserRole;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "user")
public class UserDAO implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Id
    @ManyToOne
    @JoinColumn(name = "household")
    private HouseholdDAO household;

    @Enumerated(EnumType.STRING)
    @JoinColumn(name = "role")
    private UserRole userRole;

    @JoinColumn(name = "login")
    private String login;

    @JoinColumn(name = "password")
    private String password;

    @JoinColumn(name = "name")
    private String name;

    @JoinColumn(name = "email")
    private String email;

    @JoinColumn(name = "registration_date")
    private Date registrationDate;

    @JoinColumn(name = "last_failed_login")
    private Date lastFailedLogin;

}
