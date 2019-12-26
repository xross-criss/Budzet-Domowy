package pl.dev.household.budget.manager.domain;

import lombok.*;
import pl.dev.household.budget.manager.dao.HouseholdDAO;
import pl.dev.household.budget.manager.dictionaries.InsuranceType;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Insurance {

    private Integer id;
    private HouseholdDAO household;
    private InsuranceType type;
    private String description;
    private Integer interval;
    private BigDecimal cost;
    private Date endDate;
    private String name;
    private String vehicleTID;  //    vehicle technical inspection date
    private String vehicleLP;   //    vehicle license plate

}
