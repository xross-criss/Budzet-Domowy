package pl.dev.household.budget.manager.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pl.dev.household.budget.manager.domain.LoanDTO;
import pl.dev.household.budget.manager.services.LoanService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/loan")
public class LoanController {

    private LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/{householdId}")
    public List<LoanDTO> getLoans(@RequestParam("householdId") Integer householdId) {
        return loanService.getLoans(householdId);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public LoanDTO addLoan(@RequestBody LoanDTO loanDTO) {
        return loanService.addLoan(loanDTO);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/{householdId}")
    public LoanDTO updateLoan(@RequestParam("householdId") Integer householdId, @RequestBody LoanDTO loanDTO) {
        return loanService.updateLoan(householdId, loanDTO);
    }

}
