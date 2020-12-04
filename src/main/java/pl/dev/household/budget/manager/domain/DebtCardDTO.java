package pl.dev.household.budget.manager.domain;

import lombok.*;
import pl.dev.household.budget.manager.dao.Household;
import pl.dev.household.budget.manager.dao.User;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DebtCardDTO implements Serializable {

    private Integer id;
    private UserDTO user;
    private BankDTO bank;
    private BigDecimal limit;
    private BigDecimal balance;
    private BigDecimal renewalPercentage;
    private BigDecimal annualPercentage;
    private String name;

}
