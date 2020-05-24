package pl.dev.household.budget.manager.domain;

import lombok.*;
import pl.dev.household.budget.manager.dictionaries.InvestmentCategory;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class InvestmentDTO {

    private Integer id;
    private HouseholdDTO householdDTO;
    private InvestmentCategory type;
    private Boolean isMonthly;
    private Integer period;     //    period of investment in months
    private Date endDate;
    private BigDecimal investmentPercentage;
    private BigDecimal amount;
    private String name;

}
