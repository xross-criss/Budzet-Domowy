package pl.dev.household.budget.manager.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.dev.household.budget.manager.domain.BalanceDTO;
import pl.dev.household.budget.manager.security.util.Security;
import pl.dev.household.budget.manager.services.BalanceService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/balance")
public class BalanceController {

    private BalanceService balanceService;

    public BalanceController(BalanceService balanceService) {
        this.balanceService = balanceService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<BalanceDTO>> getBalances() {
        return ResponseEntity.ok(balanceService.getBalancesForHousehold(Security.currentUser().getHousehold().getId()));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/generate")
    public ResponseEntity<BalanceDTO> generateAndReturnBalances() {
        return ResponseEntity.ok(balanceService.generateAndReturnBalance(Security.currentUser().getHousehold().getId()));
    }

}
