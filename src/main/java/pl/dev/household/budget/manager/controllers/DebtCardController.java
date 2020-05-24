package pl.dev.household.budget.manager.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pl.dev.household.budget.manager.domain.DebtCardDTO;
import pl.dev.household.budget.manager.services.DebtCardService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/debtcard")
public class DebtCardController {

    private DebtCardService debtCardService;

    public DebtCardController(DebtCardService debtCardService) {
        this.debtCardService = debtCardService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/{householdId}")
    public List<DebtCardDTO> getDebtCard(@RequestParam("householdId") Integer householdId) {
        return debtCardService.getDebtCards(householdId);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public DebtCardDTO addDebtCard(@RequestBody DebtCardDTO debtCardDTO) {
        return debtCardService.addDebtCard(debtCardDTO);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/{householdId}")
    public DebtCardDTO updateDebtCard(@RequestParam("householdId") Integer householdId, @RequestBody DebtCardDTO debtCardDTO) {
        return debtCardService.updateDebtCard(householdId, debtCardDTO);
    }

}
