package pl.dev.household.budget.manager.services;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import pl.dev.household.budget.manager.dao.Goal;
import pl.dev.household.budget.manager.dao.repository.GoalRepository;
import pl.dev.household.budget.manager.dao.repository.HouseholdRepository;
import pl.dev.household.budget.manager.dictionaries.BalanceType;
import pl.dev.household.budget.manager.domain.BalanceDTO;
import pl.dev.household.budget.manager.domain.GoalDTO;
import pl.dev.household.budget.manager.security.util.Security;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class GoalsService {

    private ModelMapper modelMapper;
    private GoalRepository goalsRepository;
    private BalanceService balanceService;
    private HouseholdRepository householdRepository;

    public GoalsService(ModelMapper modelMapper, GoalRepository goalsRepository, BalanceService balanceService, HouseholdRepository householdRepository) {
        this.modelMapper = modelMapper;
        this.goalsRepository = goalsRepository;
        this.balanceService = balanceService;
        this.householdRepository = householdRepository;
    }

    public List<GoalDTO> getGoals(Integer householdId) {
        Optional<List<Goal>> optList = Optional.of(goalsRepository.findAllByHousehold_Id(householdId).orElse(Collections.emptyList()));

        return countGoalsPercentage(Security.currentUser().getHousehold().getId(),
                optList.stream()
                        .flatMap(Collection::stream)
                        .map(goal -> modelMapper.map(goal, GoalDTO.class))
                        .collect(Collectors.toList()));
    }

    public GoalDTO getGoal(Integer goalId) {
        return countGoalsPercentage(
                Security.currentUser().getHousehold().getId(),
                modelMapper.map(goalsRepository.findById(goalId), GoalDTO.class
                ));
    }

    public GoalDTO addGoal(GoalDTO goal) {
        Integer goalId = goalsRepository.save(modelMapper.map(goal, Goal.class)).getId();
        return getGoal(goalId);
    }

    public void updateGoal(Integer householdId, GoalDTO goal) {
        Goal tmpGoal = modelMapper.map(goal, Goal.class);

        if (tmpGoal.getHousehold() == null) {
            tmpGoal.setHousehold(householdRepository.findById(householdId).get());
        }

        goalsRepository.save(tmpGoal);

    }

    private GoalDTO countGoalsPercentage(Integer householdId, GoalDTO GoalDTO) {
        return countGoalsPercentage(householdId, Collections.singletonList(GoalDTO)).get(0);
    }

    private List<GoalDTO> countGoalsPercentage(Integer householdId, List<GoalDTO> GoalDTOList) {
        BalanceDTO balance = balanceService.aggregateAndGenerate(householdId, BalanceType.GENERATED, LocalDate.now());
        BigDecimal amount = balance.getBalance();

        GoalDTOList.sort(Comparator.comparing(GoalDTO::getPriority));

        for (int i = 0; i < GoalDTOList.size(); i++) {
            GoalDTO tmpGoal = GoalDTOList.get(i);

            if (amount.compareTo(BigDecimal.ZERO) == 0) {
                tmpGoal.setPercent(0);
            } else {
                BigDecimal goalAmount = GoalDTOList.get(i).getAmount();
                if (amount.compareTo(goalAmount) >= 0) {
                    amount = amount.subtract(goalAmount);
                    tmpGoal.setPercent(100);
                } else {
                    tmpGoal.setPercent(amount.divide(goalAmount, RoundingMode.CEILING).multiply(BigDecimal.valueOf(100)).intValue());
                }
            }
            GoalDTOList.remove(i);
            GoalDTOList.add(i, tmpGoal);
        }

        return GoalDTOList;
    }
}
