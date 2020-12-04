package pl.dev.household.budget.manager.domain;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class LoanDTO implements Serializable {

    private Integer id;

    private UserDTO user;
    private BankDTO bank;

    private BigDecimal annualLoanPercentage;
    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal amount;
    private BigDecimal installmentAmount;

}
