package pl.dev.household.budget.manager.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.dev.household.budget.manager.domain.BalanceDTO;
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

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/{householdId}")
    public List<BalanceDTO> getBalances(@RequestParam("householdId") Integer householdId) {
        return balanceService.getBalancesForHousehold(householdId);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/{householdId}/generate")
    public List<BalanceDTO> generateAndReturnBalances(@RequestParam("householdId") Integer householdId) {
        return balanceService.generateAndReturnBalances(householdId);
    }

}
