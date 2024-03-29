package pl.dev.household.budget.manager.domain;

import lombok.*;
import pl.dev.household.budget.manager.dictionaries.BalanceType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BalanceDTO implements Serializable {

    private Integer id;
    private HouseholdDTO household;
    private BalanceType type;

    private LocalDate generationDate;
    private BigDecimal burden;
    private BigDecimal income;
    private BigDecimal balance;

}
