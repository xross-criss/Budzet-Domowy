package pl.dev.household.budget.manager.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pl.dev.household.budget.manager.domain.Cashflow;
import pl.dev.household.budget.manager.domain.Household;
import pl.dev.household.budget.manager.services.CashflowService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/cashflow")
public class CashflowController {

    private final CashflowService cashflowService;

    @Autowired
    public CashflowController(CashflowService cashflowService) {
        this.cashflowService = cashflowService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/{householdId}")
    public List<Cashflow> getCashflows(@RequestParam("householdId") Integer householdId) {
        return cashflowService.getCashflows(householdId);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Cashflow addCashflow(@RequestBody Cashflow cashflow) {
        return cashflowService.addCashflow(cashflow);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/{householdId}")
    public Cashflow updateCashflow(@RequestParam("householdId") Integer householdId, @RequestBody Cashflow cashflow) {
        return cashflowService.updateCashflow(householdId, cashflow);
    }

}
