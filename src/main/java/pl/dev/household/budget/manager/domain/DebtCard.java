package pl.dev.household.budget.manager.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class DebtCard {

    private Integer id;
    private Household household;
    private BigDecimal limit;
    private BigDecimal balance;
    private BigDecimal renewalPercentage;
    private BigDecimal annualPercentage;
    private String bankName;
    private String description;
}
