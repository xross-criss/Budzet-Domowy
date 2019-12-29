package pl.dev.household.budget.manager.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pl.dev.household.budget.manager.domain.Loan;
import pl.dev.household.budget.manager.services.LoanService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/loan")
public class LoanController {

    LoanService loanService;

    @Autowired
    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/{householdId}")
    public List<Loan> getLoans(@RequestParam("householdId") Integer householdId) {
        return loanService.getLoans(householdId);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Loan addLoan(@RequestBody Loan loan) {
        return loanService.addLoan(loan);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/{householdId}")
    public Loan updateLoan(@RequestParam("householdId") Integer householdId, @RequestBody Loan loan) {
        return loanService.updateLoan(householdId, loan);
    }

}
