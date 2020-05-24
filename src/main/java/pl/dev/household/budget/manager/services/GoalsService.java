package pl.dev.household.budget.manager.services;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.dev.household.budget.manager.dao.Goals;
import pl.dev.household.budget.manager.dao.repository.GoalsRepository;
import pl.dev.household.budget.manager.domain.GoalsDTO;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class GoalsService {

    private ModelMapper modelMapper;
    private GoalsRepository goalsRepository;

    @Autowired
    public GoalsService(GoalsRepository goalsRepository) {
        this.goalsRepository = goalsRepository;
    }

    public List<GoalsDTO> getGoals(Integer householdId) {
        return goalsRepository.findAllByHousehold_Id(householdId).stream()
                .map(goal -> modelMapper.map(goal, GoalsDTO.class))
                .collect(Collectors.toList());
    }

    public GoalsDTO getGoal(Integer goalId) {
        return modelMapper.map(goalsRepository.findById(goalId), GoalsDTO.class);
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
}
