package pl.dev.household.budget.manager.dao;

import lombok.Getter;
import lombok.Setter;
import pl.dev.household.budget.manager.dictionaries.InsuranceType;
import pl.dev.household.budget.manager.dictionaries.InvestmentCategory;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
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
    private InvestmentCategory type;

    @Column(name = "period")
    private Integer period;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "investment_percentage")
    private BigDecimal investmentPercentage;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "name")
    private String name;

}
