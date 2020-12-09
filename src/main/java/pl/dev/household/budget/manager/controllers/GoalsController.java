package pl.dev.household.budget.manager.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.dev.household.budget.manager.domain.GoalDTO;
import pl.dev.household.budget.manager.security.util.Security;
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

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<GoalDTO>> getGoals() {
        return ResponseEntity.ok(goalsService.getGoals(Security.currentUser().getHousehold().getId()));
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GoalDTO> addGoals(@RequestBody GoalDTO goal) {
        return ResponseEntity.ok(goalsService.addGoal(goal));
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public void updateGoals(@RequestBody GoalDTO goal) {
        goalsService.updateGoal(Security.currentUser().getHousehold().getId(), goal);
    }

}
