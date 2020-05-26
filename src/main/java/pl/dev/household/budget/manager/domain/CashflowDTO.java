package pl.dev.household.budget.manager.domain;

import lombok.*;
import pl.dev.household.budget.manager.dictionaries.CashflowCategory;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CashflowDTO {

    private Integer id;
    private HouseholdDTO householdDTO;
    private CashflowCategory category;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer interval;
    private BigDecimal amount;
    private String description;

}
