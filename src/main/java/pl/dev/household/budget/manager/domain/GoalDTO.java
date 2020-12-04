package pl.dev.household.budget.manager.domain;

import lombok.*;
import pl.dev.household.budget.manager.dao.Household;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class GoalDTO implements Serializable {

    private Integer id;
    private HouseholdDTO household;
    Set<CategoryDTO> category;
    private BigDecimal amount;
    private String name;
    private String description;
    private Integer priority;
    private Integer percent;

}
