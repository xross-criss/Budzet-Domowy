package pl.dev.household.budget.manager.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pl.dev.household.budget.manager.domain.Insurance;
import pl.dev.household.budget.manager.domain.Investment;
import pl.dev.household.budget.manager.services.InvestmentService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/investment")
public class InvestmentController {

    private InvestmentService investmentService;

    @Autowired
    public InvestmentController(InvestmentService investmentService) {
        this.investmentService = investmentService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/{householdId}")
    public List<Investment> getInvestments(@RequestParam("householdId") Integer householdId) {
        return investmentService.getInvestments(householdId);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Investment addInvestment(@RequestBody Investment investment) {
        return investmentService.addInvestment(investment);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/{householdId}")
    public Investment updateInvestment(@RequestParam("householdId") Integer householdId, @RequestBody Investment investment) {
        return investmentService.updateInvestment(householdId, investment);
    }

}
