package pl.dev.household.budget.manager.dao;

import pl.dev.household.budget.manager.dictionaries.CashflowCategory;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "cashflow")
public class CashflowDAO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "household")
    private HouseholdDAO household;

    @Enumerated(EnumType.STRING)
    @JoinColumn(name = "category")
    private CashflowCategory category;

    @JoinColumn(name = "start_date")
    private Date startDate;

    @JoinColumn(name = "end_date")
    private Date endDate;

    @JoinColumn(name = "interval")
    private Integer interval;

    @JoinColumn(name = "amount")
    private BigDecimal amount;

    @JoinColumn(name = "description")
    private String description;
}
