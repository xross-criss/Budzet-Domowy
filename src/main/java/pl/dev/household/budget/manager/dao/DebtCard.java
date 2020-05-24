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
public class DebtCard implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "household")
    private Household household;

    @Column(name = "limit")
    private BigDecimal limit;

    @Column(name = "balance")
    private BigDecimal balance;

    @Column(name = "renewal_percentage")
    private BigDecimal renewalPercentage;

    @Column(name = "annual_percentage")
    private BigDecimal annualPercentage;

    @Column(name = "bank")
    private String bank;

    @Column(name = "name")
    private String name;

}
