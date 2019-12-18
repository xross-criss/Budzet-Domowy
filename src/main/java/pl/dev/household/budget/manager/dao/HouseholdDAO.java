package pl.dev.household.budget.manager.dao;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "household")
public class HouseholdDAO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "population")
    private Integer population;

    @JoinColumn(name = "cost")
    private BigDecimal cost;

}
