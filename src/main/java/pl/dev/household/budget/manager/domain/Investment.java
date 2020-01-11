package pl.dev.household.budget.manager.domain;

import lombok.*;
import pl.dev.household.budget.manager.dao.HouseholdDAO;
import pl.dev.household.budget.manager.dictionaries.InsuranceType;
import pl.dev.household.budget.manager.dictionaries.InvestmentCategory;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Investment {

    private Integer id;
    private Household household;
    private InvestmentCategory type;
    private Boolean isMonthly;
    private Integer period;     //    period of investment in months
    private Date endDate;
    private BigDecimal investmentPercentage;
    private BigDecimal amount;
    private String name;

}
