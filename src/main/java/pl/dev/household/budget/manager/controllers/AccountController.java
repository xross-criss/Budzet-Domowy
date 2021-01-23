package pl.dev.household.budget.manager.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.dev.household.budget.manager.domain.AccountDTO;
import pl.dev.household.budget.manager.domain.ReportIntDTO;
import pl.dev.household.budget.manager.security.util.Security;
import pl.dev.household.budget.manager.services.AccountService;
import pl.dev.household.budget.manager.services.UserService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/account")
public class AccountController {

    private AccountService accountService;
    private UserService userService;

    public AccountController(AccountService accountService, UserService userService) {
        this.accountService = accountService;
        this.userService = userService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AccountDTO>> getAccounts() throws Exception {
        return ResponseEntity.ok(accountService.getAccountsInHousehold(Security.currentUser().getHousehold().getId()));
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AccountDTO> addAccount(@RequestBody AccountDTO accountDTO) {
        return ResponseEntity.ok(accountService.addAccount(accountDTO));
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public void updateAccount(@RequestBody AccountDTO accountDTO) throws Exception {
        accountService.updateAccount(accountDTO, Security.currentUser());
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
