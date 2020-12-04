package pl.dev.household.budget.manager.domain;

import lombok.*;
import pl.dev.household.budget.manager.dao.User;
import pl.dev.household.budget.manager.dictionaries.CashflowCategory;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CashflowDTO implements Serializable {

    private Integer id;
    private UserDTO user;
    private CategoryDTO category;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer period;
    private BigDecimal amount;
    private String description;

}
