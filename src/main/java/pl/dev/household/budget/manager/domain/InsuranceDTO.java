package pl.dev.household.budget.manager.domain;

import lombok.*;
import pl.dev.household.budget.manager.dictionaries.InsuranceType;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class InsuranceDTO {

    private Integer id;
    private HouseholdDTO household;
    private InsuranceType type;
    private String description;
    private Integer period;
    private BigDecimal cost;
    private LocalDate endDate;
    private String name;
    private String vehicleTID;  //    vehicle technical inspection date
    private String vehicleLP;   //    vehicle license plate

}
