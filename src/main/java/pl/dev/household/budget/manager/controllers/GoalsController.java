package pl.dev.household.budget.manager.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pl.dev.household.budget.manager.domain.GoalsDTO;
import pl.dev.household.budget.manager.services.GoalsService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/goals")
public class GoalsController {

    private GoalsService goalsService;

    public GoalsController(GoalsService goalsService) {
        this.goalsService = goalsService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/{householdId}")
    public List<GoalsDTO> getGoals(@RequestParam("householdId") Integer householdId) {
        return goalsService.getGoals(householdId);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public GoalsDTO addGoals(@RequestBody GoalsDTO goal) {
        return goalsService.addGoal(goal);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/{householdId}")
    public GoalsDTO updateGoals(@RequestParam("householdId") Integer householdId, @RequestBody GoalsDTO goal) {
        return goalsService.updateGoal(householdId, goal);
    }

}
