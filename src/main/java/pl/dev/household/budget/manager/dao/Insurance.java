package pl.dev.household.budget.manager.dao;

import lombok.Getter;
import lombok.Setter;
import pl.dev.household.budget.manager.dictionaries.InsuranceType;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "insurance")
public class Insurance implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user")
    private User user;

    @ManyToOne
    @JoinColumn(name = "vehicle")
    private Vehicle vehicle;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private InsuranceType type;

    @Column(name = "description")
    private String description;

    @Column(name = "period")
    private Integer period;

    @Column(name = "cost")
    private BigDecimal cost;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "name")
    private String name;
}
