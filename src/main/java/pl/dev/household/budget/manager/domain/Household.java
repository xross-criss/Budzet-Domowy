package pl.dev.household.budget.manager.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class Household {

    private Integer id;
    private Integer population;
    private BigDecimal cost;
//    base cost of household
}
