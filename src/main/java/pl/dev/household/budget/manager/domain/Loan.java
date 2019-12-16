package pl.dev.household.budget.manager.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class Loan {

    private Integer id;
    private Household household;
    private String bankName;
    private BigDecimal annualLoanPercentage;
    private Date startingDate;
    private Date endingDate;
    private BigDecimal amount;
    private BigDecimal installment_amount;

}
