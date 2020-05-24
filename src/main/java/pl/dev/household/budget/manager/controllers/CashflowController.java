package pl.dev.household.budget.manager.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.dev.household.budget.manager.domain.CashflowDTO;
import pl.dev.household.budget.manager.security.util.Security;
import pl.dev.household.budget.manager.services.CashflowService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api")
public class CashflowController {

    private CashflowService cashflowService;

    public CashflowController(CashflowService cashflowService) {
        this.cashflowService = cashflowService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/cashflow")
    public ResponseEntity<List<CashflowDTO>> getCashflows() {
        return ResponseEntity.ok(cashflowService.getCashflows(Security.currentUser().getHousehold().getId()));
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CashflowDTO> addCashflow(@RequestBody CashflowDTO cashflowDTO) {
        return ResponseEntity.ok(cashflowService.addCashflow(cashflowDTO));
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/cashflow")
    public ResponseEntity<CashflowDTO> updateCashflow(@RequestBody CashflowDTO cashflowDTO) {
        return ResponseEntity.ok(cashflowService.updateCashflow(Security.currentUser().getHousehold().getId(), cashflowDTO));
    }

}
