package pl.dev.household.budget.manager.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import pl.dev.household.budget.manager.dictionaries.InvestmentCategory;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class Investment {

    private Integer id;
    private Household household;
    private InvestmentCategory category;
    private Boolean isMonthly;
    private Integer period;     //    period of investment in months
    private Date endingDate;
    private BigDecimal amount;
    private String description;
}
