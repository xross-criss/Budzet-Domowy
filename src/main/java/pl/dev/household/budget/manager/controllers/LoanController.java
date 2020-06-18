package pl.dev.household.budget.manager.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.dev.household.budget.manager.domain.LoanDTO;
import pl.dev.household.budget.manager.security.util.Security;
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

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<LoanDTO>> getLoans() {
        return ResponseEntity.ok(loanService.getLoans(Security.currentUser().getHousehold().getId()));
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LoanDTO> addLoan(@RequestBody LoanDTO loanDTO) {
        return ResponseEntity.ok(loanService.addLoan(loanDTO));
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LoanDTO> updateLoan(@RequestBody LoanDTO loanDTO) {
        return ResponseEntity.ok(loanService.updateLoan(Security.currentUser().getHousehold().getId(), loanDTO));
    }

}
