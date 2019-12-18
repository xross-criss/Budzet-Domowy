package pl.dev.household.budget.manager.dao;

import pl.dev.household.budget.manager.dictionaries.InsuranceType;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "investment")
public class InvestmentDAO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "household")
    private HouseholdDAO household;

    @Enumerated(EnumType.STRING)
    @JoinColumn(name = "category")
    private InsuranceType type;

    @JoinColumn(name = "is_monthly")
    private Boolean isMonthly;

    @JoinColumn(name = "period")
    private Integer period;

    @JoinColumn(name = "end_date")
    private Date endDate;

    @JoinColumn(name = "investment_percentage")
    private BigDecimal investmentPercentage;

    @JoinColumn(name = "amount")
    private BigDecimal amount;

    @JoinColumn(name = "name")
    private String name;

}
