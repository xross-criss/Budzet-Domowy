package pl.dev.household.budget.manager.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.dev.household.budget.manager.dictionaries.CashflowCategory;
import pl.dev.household.budget.manager.domain.CashflowDTO;
import pl.dev.household.budget.manager.security.util.Security;
import pl.dev.household.budget.manager.services.CashflowService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/cashflow")
public class CashflowController {

    private CashflowService cashflowService;

    public CashflowController(CashflowService cashflowService) {
        this.cashflowService = cashflowService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CashflowDTO>> getCashflows() {
        return ResponseEntity.ok(cashflowService.getCashflows(Security.currentUser().getHousehold().getId()));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/category")
    public ResponseEntity<List<CashflowDTO>> getCashflowsWithCategory(@RequestParam(name = "cat") String cat) {
        return ResponseEntity.ok(cashflowService.getCashflowsWithType(Security.currentUser().getHousehold().getId(), CashflowCategory.valueOf(cat)));
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CashflowDTO> addCashflow(@RequestBody CashflowDTO cashflowDTO) {
        return ResponseEntity.ok(cashflowService.addCashflow(cashflowDTO));
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CashflowDTO> updateCashflow(@RequestBody CashflowDTO cashflowDTO) {
        return ResponseEntity.ok(cashflowService.updateCashflow(Security.currentUser().getHousehold().getId(), cashflowDTO));
    }

}
