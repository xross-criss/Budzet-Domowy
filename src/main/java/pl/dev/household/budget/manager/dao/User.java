package pl.dev.household.budget.manager.dao;

import lombok.Getter;
import lombok.Setter;
import pl.dev.household.budget.manager.dictionaries.UserRole;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "user")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "household")
    private Household household;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private UserRole userRole;

    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "registration_date")
    private LocalDate registrationDate;

    @Column(name = "last_failed_login")
    private LocalDate lastFailedLogin;

}
