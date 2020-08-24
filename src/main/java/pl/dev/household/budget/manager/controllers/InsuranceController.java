package pl.dev.household.budget.manager.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.dev.household.budget.manager.domain.CashflowDTO;
import pl.dev.household.budget.manager.domain.InsuranceDTO;
import pl.dev.household.budget.manager.domain.ReportIntDTO;
import pl.dev.household.budget.manager.security.util.Security;
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

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<InsuranceDTO>> getInsurances() {
        return ResponseEntity.ok(insuranceService.getInsurances(Security.currentUser().getHousehold().getId()));
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<InsuranceDTO> addInsurance(@RequestBody InsuranceDTO insuranceDTO) {
        return ResponseEntity.ok(insuranceService.addInsurance(insuranceDTO));
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<InsuranceDTO> updateInsurance(@RequestBody InsuranceDTO insuranceDTO) {
        return ResponseEntity.ok(insuranceService.updateInsurance(Security.currentUser().getHousehold().getId(), insuranceDTO));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/report")
    public ResponseEntity<ReportIntDTO> generateCurrentMonthBalanceReport() {
        return ResponseEntity.ok(insuranceService.countInsuranceBalance(Security.currentUser().getHousehold().getId()));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/currmonth")
    public ResponseEntity<List<InsuranceDTO>> getCurrentMonthCashflows() {
        return ResponseEntity.ok(insuranceService.aggregateInsurancesForCurrentMonth(Security.currentUser().getHousehold().getId()));
    }

}
