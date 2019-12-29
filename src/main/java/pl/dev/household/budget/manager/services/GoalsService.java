package pl.dev.household.budget.manager.services;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.dev.household.budget.manager.dao.GoalsDAO;
import pl.dev.household.budget.manager.dao.repository.GoalsRepository;
import pl.dev.household.budget.manager.domain.Goals;

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

    public List<Goals> getGoals(Integer householdId) {
        return goalsRepository.findAllByHousehold_Id(householdId).stream()
                .map(goal -> modelMapper.map(goal, Goals.class))
                .collect(Collectors.toList());
    }

    public Goals getGoal(Integer goalId) {
        return modelMapper.map(goalsRepository.findById(goalId), Goals.class);
    }

    public Goals addGoal(Goals goal) {
        Integer goalId = goalsRepository.save(modelMapper.map(goal, GoalsDAO.class)).getId();
        return getGoal(goalId);
    }

    public Goals updateGoal(Integer householdId, Goals goal) {
        Optional<GoalsDAO> oldGoal = goalsRepository.findById(goal.getId());
        if (oldGoal.isEmpty() || !oldGoal.get().getId().equals(goal.getId())) {
            throw new RuntimeException("Goals cannot be updated!");
        }

        GoalsDAO updatedGoal = modelMapper.map(goal, GoalsDAO.class);
        goalsRepository.save(updatedGoal);

        return getGoal(updatedGoal.getId());
    }
}
