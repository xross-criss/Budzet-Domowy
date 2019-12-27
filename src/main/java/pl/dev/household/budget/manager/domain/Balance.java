package pl.dev.household.budget.manager.domain;

import lombok.*;
import pl.dev.household.budget.manager.dictionaries.BalanceType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Balance implements Serializable {

    private Integer id;
    private Household household;
    private BalanceType type;
    private Date generationDate;
    private BigDecimal burden;
    private BigDecimal income;
    private BigDecimal balance;

}
