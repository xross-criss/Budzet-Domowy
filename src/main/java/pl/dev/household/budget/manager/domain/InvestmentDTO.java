package pl.dev.household.budget.manager.domain;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class InvestmentDTO implements Serializable {

    private Integer id;

    private UserDTO user;
    Set<CategoryDTO> category;

    private Integer period;
    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal investmentPercentage;
    private BigDecimal amount;
    private String name;

}
