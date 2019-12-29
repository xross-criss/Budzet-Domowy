package pl.dev.household.budget.manager.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pl.dev.household.budget.manager.domain.DebtCard;
import pl.dev.household.budget.manager.services.DebtCardService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/debtcard")
public class DebtCardController {

    private DebtCardService debtCardService;

    @Autowired
    public DebtCardController(DebtCardService debtCardService) {
        this.debtCardService = debtCardService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/{householdId}")
    public List<DebtCard> getDebtCard(@RequestParam("householdId") Integer householdId) {
        return debtCardService.getDebtCards(householdId);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public DebtCard addDebtCard(@RequestBody DebtCard debtCard) {
        return debtCardService.addDebtCard(debtCard);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/{householdId}")
    public DebtCard updateDebtCard(@RequestParam("householdId") Integer householdId, @RequestBody DebtCard debtCard) {
        return debtCardService.updateDebtCard(householdId, debtCard);
    }

}
