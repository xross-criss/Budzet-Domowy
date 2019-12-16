package pl.dev.household.budget.manager.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import pl.dev.household.budget.manager.dictionaries.InsuranceType;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class Insurance {

    private Integer id;
    private Household household;
    private InsuranceType type;
    private String description;
    private Integer interval;
    private BigDecimal cost;
    private Date endDate;
    private String vehicleTID;  //    vehicle technical inspection date
    private String vehicleLP;   //    vehicle license plate

}
