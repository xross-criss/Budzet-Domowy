package pl.dev.household.budget.manager.dao;

import lombok.Getter;
import lombok.Setter;
import pl.dev.household.budget.manager.dictionaries.InsuranceType;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "investment")
public class Investment implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "household")
    private Household household;

    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    private InsuranceType type;

    @Column(name = "is_monthly")
    private Boolean isMonthly;

    @Column(name = "period")
    private Integer period;

    @Column(name = "end_date")
    private Date endDate;

    @Column(name = "investment_percentage")
    private BigDecimal investmentPercentage;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "name")
    private String name;

}
