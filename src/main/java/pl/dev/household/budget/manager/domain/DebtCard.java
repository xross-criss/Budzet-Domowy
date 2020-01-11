package pl.dev.household.budget.manager.domain;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DebtCard {

    private Integer id;
    private Household household;
    private BigDecimal limit;
    private BigDecimal balance;
    private BigDecimal renewalPercentage;
    private BigDecimal annualPercentage;
    private String bank;
    private String name;
}
