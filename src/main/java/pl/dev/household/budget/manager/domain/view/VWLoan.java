package pl.dev.household.budget.manager.domain.view;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import pl.dev.household.budget.manager.domain.Household;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class VWLoan {

    private Integer id;
    private Household household;
    private String bankName;
    private BigDecimal annualLoanPercentage;
    private Date startingDate;
    private Date endingDate;
    private BigDecimal amount;
    private Integer fullInstallmentDuration;
    private Integer installmentDurationToGo;
    private BigDecimal installmentAmount;

}
