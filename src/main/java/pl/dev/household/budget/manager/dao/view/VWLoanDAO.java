package pl.dev.household.budget.manager.dao.view;

import pl.dev.household.budget.manager.dao.HouseholdDAO;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "vw_loan")
public class VWLoanDAO {

    @Id
    @JoinColumn(name = "id")
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

    @JoinColumn(name = "full_installment_duration")
    private Integer fullInstallmentDuration;

    @JoinColumn(name = "installment_duration_to_go")
    private Integer installmentDurationToGo;

    @JoinColumn(name = "installment_amount")
    private BigDecimal installmentAmount;

}
