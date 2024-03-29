package pl.dev.household.budget.manager.domain.view;

import lombok.*;
import pl.dev.household.budget.manager.domain.HouseholdDTO;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class VWLoanDTO {

    private Integer id;
    private HouseholdDTO household;
    private String bankName;
    private BigDecimal annualLoanPercentage;

    private LocalDate startDate;

    private LocalDate endDate;
    private BigDecimal amount;
    private Integer fullInstallmentDuration;
    private Integer installmentDurationToGo;
    private BigDecimal installmentAmount;

}
