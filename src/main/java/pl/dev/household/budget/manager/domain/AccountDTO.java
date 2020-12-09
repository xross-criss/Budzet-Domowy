package pl.dev.household.budget.manager.domain;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AccountDTO {

    private Integer id;
    private UserDTO user;
    private BankDTO bank;
    private BigDecimal amount;

}
