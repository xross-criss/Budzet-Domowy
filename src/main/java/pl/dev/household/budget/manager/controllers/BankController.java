package pl.dev.household.budget.manager.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.dev.household.budget.manager.domain.BankDTO;
import pl.dev.household.budget.manager.domain.ReportIntDTO;
import pl.dev.household.budget.manager.security.util.Security;
import pl.dev.household.budget.manager.services.BankService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/bank")
public class BankController {

    private BankService bankService;

    public BankController(BankService bankService) {
        this.bankService = bankService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<BankDTO>> getBanks() {
        return ResponseEntity.ok(bankService.getBankList(Security.currentUser().getHousehold().getId()));
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BankDTO> addBank(@RequestBody BankDTO bankDTO) {
        return ResponseEntity.ok(bankService.saveBank(bankDTO));
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public void updateBank(@RequestBody BankDTO bankDTO) throws Exception {
        bankService.updateBank(Security.currentUser().getHousehold(), bankDTO);
    }

    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public void deleteBank(@RequestParam(name = "id") Integer bankId) throws Exception {
        bankService.deleteBank(bankId);
    }

}
