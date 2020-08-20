package pl.dev.household.budget.manager.domain;

import lombok.*;
import pl.dev.household.budget.manager.dictionaries.GoalCategory;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class GoalsDTO {

    private Integer id;
    private HouseholdDTO household;
    private GoalCategory category;
    private BigDecimal amount;
    private Integer percent;
    private String name;
    private String description;
    private Integer priority;

}
