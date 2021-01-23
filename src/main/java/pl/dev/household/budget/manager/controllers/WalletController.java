package pl.dev.household.budget.manager.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.dev.household.budget.manager.domain.ReportIntDTO;
import pl.dev.household.budget.manager.domain.WalletDTO;
import pl.dev.household.budget.manager.security.util.Security;
import pl.dev.household.budget.manager.services.WalletService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/wallet")
public class WalletController {

    private WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<WalletDTO>> getWallets() throws Exception {
        return ResponseEntity.ok(walletService.getWalletsInHousehold(Security.currentUser().getHousehold().getId()));
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WalletDTO> addWallet(@RequestBody WalletDTO walletDTO) {
        return ResponseEntity.ok(walletService.saveWallet(walletDTO));
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public void updateWallet(@RequestBody WalletDTO walletDTO) throws Exception {
        walletService.updateWallet(Security.currentUser(), walletDTO);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/report")
    public ResponseEntity<ReportIntDTO> generateCurrentMonthBalanceReport() throws Exception {
        return ResponseEntity.ok(walletService.countWalletBalance(Security.currentUser().getHousehold().getId()));
    }

    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public void deleteWallet(@RequestParam(name = "id") Integer walletId) throws Exception {
        walletService.deleteWallet(walletId);
    }

}
