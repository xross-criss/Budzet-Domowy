package pl.dev.household.budget.manager.dao;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "loan")
public class LoanDAO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "household")
    private HouseholdDAO household;

    @JoinColumn(name = "bank")
    private String bankName;

    @JoinColumn(name = "annual_loan_percentage")
    private BigDecimal annualLoanPercentage;

    @JoinColumn(name = "start_date")
    private Date startDate;

    @JoinColumn(name = "end_date")
    private Date endDate;

    @JoinColumn(name = "amount")
    private BigDecimal amount;

    @JoinColumn(name = "installment_amount")
    private BigDecimal installmentAmount;

}
