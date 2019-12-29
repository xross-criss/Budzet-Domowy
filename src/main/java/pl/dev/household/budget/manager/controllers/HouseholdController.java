package pl.dev.household.budget.manager.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pl.dev.household.budget.manager.domain.Household;
import pl.dev.household.budget.manager.services.HouseholdService;

@Slf4j
@RestController
@RequestMapping("/api/household")
public class HouseholdController {

    private HouseholdService householdService;

    @Autowired
    public HouseholdController(HouseholdService householdService) {
        this.householdService = householdService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/{householdId}")
    public Household getHousehold(@RequestParam("householdId") Integer householdId) {
        return householdService.getHousehold(householdId);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Household addHousehold(@RequestBody Household household) {
        return householdService.addHousehold(household);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/{householdId}")
    public Household updateHousehold(@RequestParam("householdId") Integer householdId, @RequestBody Household household) {
        return householdService.updateHousehold(householdId, household);
    }

}
