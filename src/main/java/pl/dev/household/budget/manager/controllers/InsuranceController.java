package pl.dev.household.budget.manager.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pl.dev.household.budget.manager.domain.Insurance;
import pl.dev.household.budget.manager.services.InsuranceService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/insurance")
public class InsuranceController {

    private InsuranceService insuranceService;

    @Autowired
    public InsuranceController(InsuranceService insuranceService) {
        this.insuranceService = insuranceService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/{householdId}")
    public List<Insurance> getInsurances(@RequestParam("householdId") Integer householdId) {
        return insuranceService.getInsurances(householdId);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Insurance addInsurance(@RequestBody Insurance insurance) {
        return insuranceService.addInsurance(insurance);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/{householdId}")
    public Insurance updateInsurance(@RequestParam("householdId") Integer householdId, @RequestBody Insurance insurance) {
        return insuranceService.updateInsurance(householdId, insurance);
    }

}
