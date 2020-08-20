package pl.dev.household.budget.manager.services;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import pl.dev.household.budget.manager.dao.Goals;
import pl.dev.household.budget.manager.dao.repository.GoalsRepository;
import pl.dev.household.budget.manager.dictionaries.BalanceType;
import pl.dev.household.budget.manager.domain.BalanceDTO;
import pl.dev.household.budget.manager.domain.GoalsDTO;
import pl.dev.household.budget.manager.security.util.Security;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class GoalsService {

    private ModelMapper modelMapper;
    private GoalsRepository goalsRepository;
    private BalanceService balanceService;

    public GoalsService(ModelMapper modelMapper, GoalsRepository goalsRepository, BalanceService balanceService) {
        this.modelMapper = modelMapper;
        this.goalsRepository = goalsRepository;
        this.balanceService = balanceService;
    }

    public List<GoalsDTO> getGoals(Integer householdId) {
        return countGoalsPercentage(Security.currentUser().getHousehold().getId(),
                goalsRepository.findAllByHousehold_Id(householdId).stream()
                        .map(goal -> modelMapper.map(goal, GoalsDTO.class))
                        .collect(Collectors.toList()));
    }

    public GoalsDTO getGoal(Integer goalId) {
        return countGoalsPercentage(
                Security.currentUser().getHousehold().getId(),
                modelMapper.map(goalsRepository.findById(goalId), GoalsDTO.class
                ));
    }

    public GoalsDTO addGoal(GoalsDTO goal) {
        Integer goalId = goalsRepository.save(modelMapper.map(goal, Goals.class)).getId();
        return getGoal(goalId);
    }

    public GoalsDTO updateGoal(Integer householdId, GoalsDTO goal) {
        Optional<Goals> oldGoal = goalsRepository.findById(goal.getId());
        if (oldGoal.isEmpty() || !oldGoal.get().getId().equals(goal.getId())) {
            throw new RuntimeException("Goals cannot be updated!");
        }

        Goals updatedGoal = modelMapper.map(goal, Goals.class);
        goalsRepository.save(updatedGoal);

        return getGoal(updatedGoal.getId());
    }

    private GoalsDTO countGoalsPercentage(Integer householdId, GoalsDTO goalsDTO) {
        return countGoalsPercentage(householdId, Collections.singletonList(goalsDTO)).get(0);
    }

    private List<GoalsDTO> countGoalsPercentage(Integer householdId, List<GoalsDTO> goalsDTOList) {
        BalanceDTO balance = balanceService.aggregateAndGenerate(householdId, BalanceType.GENERATED, LocalDate.now());
        BigDecimal amount = balance.getBalance();

        goalsDTOList.sort(Comparator.comparing(GoalsDTO::getPriority));

        for (int i = 0; i < goalsDTOList.size(); i++) {
            GoalsDTO tmpGoal = goalsDTOList.get(i);

            if (amount.compareTo(BigDecimal.ZERO) == 0) {
                tmpGoal.setPercent(0);
            } else {
                BigDecimal goalAmount = goalsDTOList.get(i).getAmount();
                if (amount.compareTo(goalAmount) >= 0) {
                    amount = amount.subtract(goalAmount);
                    tmpGoal.setPercent(100);
                } else {
                    tmpGoal.setPercent(amount.divide(goalAmount, RoundingMode.CEILING).multiply(BigDecimal.valueOf(100)).intValue());
                }
            }
            goalsDTOList.remove(i);
            goalsDTOList.add(i, tmpGoal);
        }

        return goalsDTOList;
    }
}
