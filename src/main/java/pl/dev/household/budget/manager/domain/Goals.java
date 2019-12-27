package pl.dev.household.budget.manager.domain;

import lombok.*;
import pl.dev.household.budget.manager.dao.HouseholdDAO;
import pl.dev.household.budget.manager.dictionaries.GoalCategory;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Goals {

    private Integer id;
    private Household household;
    private GoalCategory category;
    private BigDecimal amount;
    private String name;
    private String description;
    private Integer priority;

}
