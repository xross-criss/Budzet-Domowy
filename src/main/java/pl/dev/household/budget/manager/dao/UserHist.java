package pl.dev.household.budget.manager.dao;

import lombok.Getter;
import lombok.Setter;
import pl.dev.household.budget.manager.dictionaries.UserRole;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "user_hist")
public class UserHist implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JoinColumn(name = "user_id")
    private Integer userId;

    @JoinColumn(name = "operation")
    private String operation;

    @JoinColumn(name = "operation_date")
    private LocalDate operationDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "household")
    private Household household;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "household_old")
    private Household householdOld;

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

    @Enumerated(EnumType.STRING)
    @Column(name = "role_old")
    private UserRole userRoleOld;

    @Column(name = "login_old")
    private String loginOld;

    @Column(name = "password_old")
    private String passwordOld;

    @Column(name = "name_old")
    private String nameOld;

    @Column(name = "email_old")
    private String emailOld;

}
