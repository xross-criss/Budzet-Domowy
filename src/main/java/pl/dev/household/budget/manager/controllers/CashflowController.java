package pl.dev.household.budget.manager.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.dev.household.budget.manager.dictionaries.CashflowCategory;
import pl.dev.household.budget.manager.domain.BalanceDTO;
import pl.dev.household.budget.manager.domain.CashflowDTO;
import pl.dev.household.budget.manager.domain.ReportIntDTO;
import pl.dev.household.budget.manager.security.util.Security;
import pl.dev.household.budget.manager.services.CashflowService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/cashflow")
public class CashflowController {

    private CashflowService cashflowService;

    public CashflowController(CashflowService cashflowService) {
        this.cashflowService = cashflowService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CashflowDTO>> getCashflows() throws Exception {
        return ResponseEntity.ok(cashflowService.getCashflows(Security.currentUser().getHousehold().getId()));
    }

//    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/category")
//    public ResponseEntity<List<CashflowDTO>> getCashflowsWithCategory(@RequestParam(name = "cat") String cat) {
//        return ResponseEntity.ok(cashflowService.getCashflowsWithType(Security.currentUser().getId(), CashflowCategory.valueOf(cat)));
//    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CashflowDTO> addCashflow(@RequestBody CashflowDTO cashflowDTO) {
        return ResponseEntity.ok(cashflowService.addCashflow(cashflowDTO));
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public void updateCashflow(@RequestBody CashflowDTO cashflowDTO) {
        cashflowService.updateCashflow(Security.currentUser().getId(), cashflowDTO);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/report")
    public ResponseEntity<ReportIntDTO> generateCurrentMonthBalanceReport() throws Exception {
        return ResponseEntity.ok(cashflowService.countCashflowBalance(Security.currentUser().getId()));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/currmonth")
    public ResponseEntity<List<CashflowDTO>> getCurrentMonthCashflows() throws Exception {
        return ResponseEntity.ok(cashflowService.aggregateCashflowForCurrentMonth(Security.currentUser().getHousehold().getId()));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/month")
    public ResponseEntity<List<CashflowDTO>> getBalancesForHouseholdNoMonthAgo(@RequestParam(name = "no") int no) {
        return ResponseEntity.ok(cashflowService.getCashflowsForHouseholdNoMonthAgo(Security.currentUser().getHousehold().getId(), no));
    }

}
