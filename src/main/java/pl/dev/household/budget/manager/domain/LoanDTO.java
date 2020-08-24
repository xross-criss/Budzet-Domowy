package pl.dev.household.budget.manager.domain;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class LoanDTO {

    private Integer id;
    private HouseholdDTO household;
    private String bankName;
    private BigDecimal annualLoanPercentage;
    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal amount;
    private BigDecimal installmentAmount;

}
