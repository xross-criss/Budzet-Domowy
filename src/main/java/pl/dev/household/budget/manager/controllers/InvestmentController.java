package pl.dev.household.budget.manager.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pl.dev.household.budget.manager.domain.InvestmentDTO;
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

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/{householdId}")
    public List<InvestmentDTO> getInvestments(@RequestParam("householdId") Integer householdId) {
        return investmentService.getInvestments(householdId);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public InvestmentDTO addInvestment(@RequestBody InvestmentDTO investmentDTO) {
        return investmentService.addInvestment(investmentDTO);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/{householdId}")
    public InvestmentDTO updateInvestment(@RequestParam("householdId") Integer householdId, @RequestBody InvestmentDTO investmentDTO) {
        return investmentService.updateInvestment(householdId, investmentDTO);
    }

}
