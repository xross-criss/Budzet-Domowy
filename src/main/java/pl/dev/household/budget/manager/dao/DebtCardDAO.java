package pl.dev.household.budget.manager.dao;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "debt_card")
public class DebtCardDAO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "household")
    private HouseholdDAO household;

    @JoinColumn(name = "limit")
    private BigDecimal limit;

    @JoinColumn(name = "balance")
    private BigDecimal balance;

    @JoinColumn(name = "renewal_percentage")
    private BigDecimal renewalPercentage;

    @JoinColumn(name = "annual_percentage")
    private BigDecimal annualPercentage;

    @JoinColumn(name = "bank")
    private String bank;

    @JoinColumn(name = "name")
    private String name;

}
