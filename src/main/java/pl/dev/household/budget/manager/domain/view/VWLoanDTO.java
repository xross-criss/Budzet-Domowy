package pl.dev.household.budget.manager.domain.view;

import lombok.*;
import pl.dev.household.budget.manager.domain.HouseholdDTO;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class VWLoanDTO {

    private Integer id;
    private HouseholdDTO householdDTO;
    private String bankName;
    private BigDecimal annualLoanPercentage;
    private Date startDate;
    private Date endDate;
    private BigDecimal amount;
    private Integer fullInstallmentDuration;
    private Integer installmentDurationToGo;
    private BigDecimal installmentAmount;

}
