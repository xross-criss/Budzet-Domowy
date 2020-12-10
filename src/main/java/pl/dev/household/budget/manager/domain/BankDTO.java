package pl.dev.household.budget.manager.domain;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BankDTO {

    private Integer id;
    private HouseholdDTO household;
    private String name;
    private String description;

}
