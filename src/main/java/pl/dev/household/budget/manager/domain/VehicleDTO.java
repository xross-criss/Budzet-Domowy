package pl.dev.household.budget.manager.domain;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class VehicleDTO {

    private Integer id;
    private UserDTO user;
    private String name;
    private LocalDate vehicleTID;
    private String vehicleLP;

}
