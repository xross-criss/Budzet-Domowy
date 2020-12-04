package pl.dev.household.budget.manager.domain;

import lombok.*;
import pl.dev.household.budget.manager.dao.User;
import pl.dev.household.budget.manager.dictionaries.InsuranceType;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class InsuranceDTO implements Serializable {

    private Integer id;

    private UserDTO user;
    private VehicleDTO vehicle;
    private InsuranceType type;

    private String description;
    private Integer period;
    private BigDecimal cost;
    private LocalDate endDate;
    private String name;
}
