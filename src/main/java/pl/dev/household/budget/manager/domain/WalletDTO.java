package pl.dev.household.budget.manager.domain;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class WalletDTO {

    private Integer id;
    private UserDTO user;
    private BigDecimal amount;

}
