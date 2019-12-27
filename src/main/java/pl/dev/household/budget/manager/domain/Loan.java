package pl.dev.household.budget.manager.domain;

import lombok.*;
import pl.dev.household.budget.manager.dao.HouseholdDAO;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Loan {

    private Integer id;
    private Household household;
    private String bankName;
    private BigDecimal annualLoanPercentage;
    private Date startDate;
    private Date endDate;
    private BigDecimal amount;
    private BigDecimal installmentAmount;

}
