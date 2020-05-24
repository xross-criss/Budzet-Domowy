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
@Table(name = "insurance")
public class Insurance implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "household")
    private Household household;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private InsuranceType type;

    @Column(name = "description")
    private String description;

    @Column(name = "interval")
    private Integer interval;

    @Column(name = "cost")
    private BigDecimal cost;

    @Column(name = "end_date")
    private Date endDate;

    @Column(name = "vehicle_tid")
    private String vehicleTID;

    @Column(name = "name")
    private String name;

    @Column(name = "vehicle_lp")
    private String vehicleLP;

}
