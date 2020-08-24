package pl.dev.household.budget.manager.dao.view;

import lombok.Getter;
import lombok.Setter;
import pl.dev.household.budget.manager.dao.Household;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "vw_loan")
public class VWLoan implements Serializable {

    @Id
    @JoinColumn(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "household")
    private Household household;

    @JoinColumn(name = "bank")
    private String bankName;

    @JoinColumn(name = "annual_loan_percentage")
    private BigDecimal annualLoanPercentage;

    @JoinColumn(name = "start_date")
    private LocalDate startDate;

    @JoinColumn(name = "end_date")
    private LocalDate endDate;

    @JoinColumn(name = "amount")
    private BigDecimal amount;

    @JoinColumn(name = "full_installment_duration")
    private Integer fullInstallmentDuration;

    @JoinColumn(name = "installment_duration_to_go")
    private Integer installmentDurationToGo;

    @JoinColumn(name = "installment_amount")
    private BigDecimal installmentAmount;

}
