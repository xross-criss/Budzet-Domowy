package pl.dev.household.budget.manager.domain;

import lombok.*;
import pl.dev.household.budget.manager.dictionaries.CashflowCategory;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CashflowDTO {

    private Integer id;
    private HouseholdDTO household;
    private CashflowCategory category;

    private LocalDate startDate;

    private LocalDate endDate;
    private Integer period;
    private BigDecimal amount;
    private String description;

}
