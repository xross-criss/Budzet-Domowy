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
public class InsuranceDAO implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "household")
    private HouseholdDAO household;

    @Enumerated(EnumType.STRING)
    @JoinColumn(name = "type")
    private InsuranceType type;

    @JoinColumn(name = "description")
    private String description;

    @JoinColumn(name = "interval")
    private Integer interval;

    @JoinColumn(name = "cost")
    private BigDecimal cost;

    @JoinColumn(name = "end_date")
    private Date endDate;

    @JoinColumn(name = "vehicle_tid")
    private String vehicleTID;

    @JoinColumn(name = "name")
    private String name;

    @JoinColumn(name = "vehicle_lp")
    private String vehicleLP;

}
