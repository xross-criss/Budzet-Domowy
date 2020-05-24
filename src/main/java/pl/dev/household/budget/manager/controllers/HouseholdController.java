package pl.dev.household.budget.manager.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pl.dev.household.budget.manager.domain.HouseholdDTO;
import pl.dev.household.budget.manager.services.HouseholdService;

@Slf4j
@RestController
@RequestMapping("/api/household")
public class HouseholdController {

    private HouseholdService householdService;

    public HouseholdController(HouseholdService householdService) {
        this.householdService = householdService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/{householdId}")
    public HouseholdDTO getHousehold(@RequestParam("householdId") Integer householdId) {
        return householdService.getHousehold(householdId);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public HouseholdDTO addHousehold(@RequestBody HouseholdDTO householdDTO) {
        return householdService.addHousehold(householdDTO);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/{householdId}")
    public HouseholdDTO updateHousehold(@RequestParam("householdId") Integer householdId, @RequestBody HouseholdDTO householdDTO) {
        return householdService.updateHousehold(householdId, householdDTO);
    }

}
