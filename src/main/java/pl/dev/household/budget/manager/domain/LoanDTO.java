package pl.dev.household.budget.manager.domain;

import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class LoanDTO {

    private Integer id;
    private HouseholdDTO householdDTO;
    private String bankName;
    private BigDecimal annualLoanPercentage;
    private Date startDate;
    private Date endDate;
    private BigDecimal amount;
    private BigDecimal installmentAmount;

}
