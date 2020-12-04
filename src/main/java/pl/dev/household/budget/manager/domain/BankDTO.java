package pl.dev.household.budget.manager.domain;

import lombok.*;

import javax.persistence.*;

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
