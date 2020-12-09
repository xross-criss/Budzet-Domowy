package pl.dev.household.budget.manager.domain;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BankDTO {

    private Integer id;
    private String name;
    private String description;

}
