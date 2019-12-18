package pl.dev.household.budget.manager.dao;

import pl.dev.household.budget.manager.dictionaries.GoalCategory;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "goals")
public class GoalsDAO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "household")
    private HouseholdDAO household;

    @Enumerated(EnumType.STRING)
    @JoinColumn(name = "category")
    private GoalCategory category;

    @JoinColumn(name = "amount")
    private BigDecimal amount;

    @JoinColumn(name = "name")
    private String name;

    @JoinColumn(name = "description")
    private String description;

    @JoinColumn(name = "priority")
    private Integer priority;

}
