package pl.dev.household.budget.manager.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.dev.household.budget.manager.domain.CashflowDTO;
import pl.dev.household.budget.manager.domain.InvestmentDTO;
import pl.dev.household.budget.manager.domain.ReportIntDTO;
import pl.dev.household.budget.manager.security.util.Security;
import pl.dev.household.budget.manager.services.InvestmentService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/investment")
public class InvestmentController {

    private InvestmentService investmentService;

    public InvestmentController(InvestmentService investmentService) {
        this.investmentService = investmentService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<InvestmentDTO>> getInvestments() {
        return ResponseEntity.ok(investmentService.getInvestments(Security.currentUser().getHousehold().getId()));
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<InvestmentDTO> addInvestment(@RequestBody InvestmentDTO investmentDTO) {
        return ResponseEntity.ok(investmentService.addInvestment(investmentDTO));
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<InvestmentDTO> updateInvestment(@RequestBody InvestmentDTO investmentDTO) {
        return ResponseEntity.ok(investmentService.updateInvestment(Security.currentUser().getHousehold().getId(), investmentDTO));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/report")
    public ResponseEntity<ReportIntDTO> generateCurrentMonthBalanceReport() {
        return ResponseEntity.ok(investmentService.countInvestmentBalance(Security.currentUser().getHousehold().getId()));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/currmonth")
    public ResponseEntity<List<InvestmentDTO>> getCurrentMonthCashflows() {
        return ResponseEntity.ok(investmentService.aggregateInvestmentsForCurrentMonth(Security.currentUser().getHousehold().getId()));
    }
}
