package pl.dev.household.budget.manager.domain;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO {

    private Integer id;
    private HouseholdDTO household;
    private String category;
    private String description;

}
