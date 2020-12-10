package pl.dev.household.budget.manager.domain;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class VehicleDTO {

    private Integer id;
    private UserDTO user;
    private String name;
    private String vehicle_tid;
    private String vehicle_lp;

}
