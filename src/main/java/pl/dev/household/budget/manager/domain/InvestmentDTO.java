package pl.dev.household.budget.manager.domain;

import lombok.*;
import pl.dev.household.budget.manager.dictionaries.InvestmentCategory;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class InvestmentDTO {

    private Integer id;
    private HouseholdDTO household;
    private InvestmentCategory type;
    private Integer interval;     //    interval of investment in months
    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal investmentPercentage;
    private BigDecimal amount;
    private String name;

}
