package pl.dev.household.budget.manager.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.dev.household.budget.manager.domain.HouseholdDTO;
import pl.dev.household.budget.manager.security.util.Security;
import pl.dev.household.budget.manager.services.HouseholdService;

@Slf4j
@RestController
@RequestMapping("/api/household")
public class HouseholdController {

    private HouseholdService householdService;

    public HouseholdController(HouseholdService householdService) {
        this.householdService = householdService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HouseholdDTO> getHousehold() {
        return ResponseEntity.ok(householdService.getHousehold(Security.currentUser().getHousehold().getId()));
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HouseholdDTO> addHousehold(@RequestBody HouseholdDTO householdDTO) {
        return ResponseEntity.ok(householdService.addHousehold(householdDTO));
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HouseholdDTO> updateHousehold(@RequestBody HouseholdDTO householdDTO) {
        return ResponseEntity.ok(householdService.updateHousehold(Security.currentUser().getHousehold().getId(), householdDTO));
    }

}
