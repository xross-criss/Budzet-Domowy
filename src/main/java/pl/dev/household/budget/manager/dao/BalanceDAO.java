package pl.dev.household.budget.manager.dao;

import lombok.Getter;
import lombok.Setter;
import pl.dev.household.budget.manager.dictionaries.BalanceType;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "balance")
public class BalanceDAO implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "household")
    private HouseholdDAO household;

    @Enumerated(EnumType.STRING)
    @JoinColumn(name = "type")
    private BalanceType type;

    @JoinColumn(name = "generation_date")
    private Date generationDate;

    @JoinColumn(name = "burden")
    private BigDecimal burden;

    @JoinColumn(name = "income")
    private BigDecimal income;

    @JoinColumn(name = "balance")
    private BigDecimal balance;

}
