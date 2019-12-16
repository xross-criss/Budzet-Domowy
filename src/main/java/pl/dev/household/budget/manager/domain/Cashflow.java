package pl.dev.household.budget.manager.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import pl.dev.household.budget.manager.dictionaries.CashflowCategory;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class Cashflow {

    private Integer id;
    private Household household;
    private CashflowCategory category;
    private Date startDate;
    private Date endDate;
    private Integer interval;
    private BigDecimal amount;
    private String description;
}
