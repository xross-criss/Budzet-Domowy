package pl.dev.household.budget.manager.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pl.dev.household.budget.manager.domain.CashflowDTO;
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

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/{householdId}")
    public List<CashflowDTO> getCashflows(@RequestParam("householdId") Integer householdId) {
        return cashflowService.getCashflows(householdId);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public CashflowDTO addCashflow(@RequestBody CashflowDTO cashflowDTO) {
        return cashflowService.addCashflow(cashflowDTO);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/{householdId}")
    public CashflowDTO updateCashflow(@RequestParam("householdId") Integer householdId, @RequestBody CashflowDTO cashflowDTO) {
        return cashflowService.updateCashflow(householdId, cashflowDTO);
    }

}
