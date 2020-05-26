package pl.dev.household.budget.manager.domain;

import lombok.*;
import pl.dev.household.budget.manager.dictionaries.InsuranceType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class InsuranceDTO {

    private Integer id;
    private HouseholdDTO householdDTO;
    private InsuranceType type;
    private String description;
    private Integer interval;
    private BigDecimal cost;
    private LocalDate endDate;
    private String name;
    private String vehicleTID;  //    vehicle technical inspection date
    private String vehicleLP;   //    vehicle license plate

}
