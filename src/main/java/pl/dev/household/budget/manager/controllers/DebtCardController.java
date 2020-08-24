package pl.dev.household.budget.manager.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.dev.household.budget.manager.domain.CashflowDTO;
import pl.dev.household.budget.manager.domain.DebtCardDTO;
import pl.dev.household.budget.manager.domain.ReportIntDTO;
import pl.dev.household.budget.manager.security.util.Security;
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

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<DebtCardDTO>> getDebtCards() {
        return ResponseEntity.ok(debtCardService.getDebtCards(Security.currentUser().getHousehold().getId()));
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DebtCardDTO> addDebtCard(@RequestBody DebtCardDTO debtCardDTO) {
        return ResponseEntity.ok(debtCardService.addDebtCard(debtCardDTO));
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DebtCardDTO> updateDebtCard(@RequestBody DebtCardDTO debtCardDTO) {
        return ResponseEntity.ok(debtCardService.updateDebtCard(Security.currentUser().getHousehold().getId(), debtCardDTO));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/report")
    public ResponseEntity<ReportIntDTO> generateCurrentMonthBalanceReport() {
        return ResponseEntity.ok(debtCardService.countDebtCardBalance(Security.currentUser().getHousehold().getId()));
    }

}
