package pl.dev.household.budget.manager.dao;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "debt_card")
public class DebtCardDAO implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Id
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
