package pl.dev.household.budget.manager.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pl.dev.household.budget.manager.domain.InsuranceDTO;
import pl.dev.household.budget.manager.services.InsuranceService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/insurance")
public class InsuranceController {

    private InsuranceService insuranceService;

    public InsuranceController(InsuranceService insuranceService) {
        this.insuranceService = insuranceService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/{householdId}")
    public List<InsuranceDTO> getInsurances(@RequestParam("householdId") Integer householdId) {
        return insuranceService.getInsurances(householdId);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public InsuranceDTO addInsurance(@RequestBody InsuranceDTO insuranceDTO) {
        return insuranceService.addInsurance(insuranceDTO);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/{householdId}")
    public InsuranceDTO updateInsurance(@RequestParam("householdId") Integer householdId, @RequestBody InsuranceDTO insuranceDTO) {
        return insuranceService.updateInsurance(householdId, insuranceDTO);
    }

}
