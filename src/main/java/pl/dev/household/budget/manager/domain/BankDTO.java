package pl.dev.household.budget.manager.domain;

import lombok.*;
import pl.dev.household.budget.manager.dao.Household;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BankDTO {

    private Integer id;
    private Household household;
    private String name;
    private String description;

}
