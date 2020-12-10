package pl.dev.household.budget.manager.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.dev.household.budget.manager.domain.AccountDTO;
import pl.dev.household.budget.manager.domain.ReportIntDTO;
import pl.dev.household.budget.manager.security.util.Security;
import pl.dev.household.budget.manager.services.AccountService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/account")
public class AccountController {

    private AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AccountDTO>> getAccounts() {
        return ResponseEntity.ok(accountService.getAccountsDTOForUser(Security.currentUser().getId()));
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AccountDTO> addAccount(@RequestBody AccountDTO accountDTO) {
        return ResponseEntity.ok(accountService.addAccount(accountDTO));
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public void updateAccount(@RequestBody AccountDTO accountDTO) throws Exception {
        accountService.updateAccount(accountDTO);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/report")
    public ResponseEntity<ReportIntDTO> generateCurrentMonthBalanceReport() throws Exception {
        return ResponseEntity.ok(accountService.accountAccountBalance(Security.currentUser().getHousehold().getId()));
    }

    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public void deleteAccount(@RequestParam(name = "id") Integer accountId) throws Exception {
        accountService.deleteAccount(Security.currentUser().getId(), accountId);
    }

}
