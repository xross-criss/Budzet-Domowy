package pl.dev.household.budget.manager.domain;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DebtCardDTO {

    private Integer id;
    private HouseholdDTO householdDTO;
    private BigDecimal limit;
    private BigDecimal balance;
    private BigDecimal renewalPercentage;
    private BigDecimal annualPercentage;
    private String bank;
    private String name;
}
