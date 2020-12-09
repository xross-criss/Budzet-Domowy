package pl.dev.household.budget.manager.dao;

import lombok.Getter;
import lombok.Setter;
import pl.dev.household.budget.manager.dictionaries.BalanceType;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "balance")
public class Balance implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "household")
    private Household household;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private BalanceType type;

    @Column(name = "generation_date")
    private LocalDate generationDate;

    @Column(name = "burden")
    private BigDecimal burden;

    @Column(name = "income")
    private BigDecimal income;

    @Column(name = "balance")
    private BigDecimal balance;

}
