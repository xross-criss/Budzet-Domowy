package pl.dev.household.budget.manager.domain;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Household {

    private Integer id;
    private Integer population;
    private BigDecimal cost;
//    base cost of household
}
