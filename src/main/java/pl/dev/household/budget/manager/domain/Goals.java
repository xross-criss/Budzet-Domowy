package pl.dev.household.budget.manager.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import pl.dev.household.budget.manager.dictionaries.GoalCategory;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class Goals {

    private Integer id;
    private Household household;
    private GoalCategory category;
    private BigDecimal amount;
    private String description;
    private Integer priority;
}
