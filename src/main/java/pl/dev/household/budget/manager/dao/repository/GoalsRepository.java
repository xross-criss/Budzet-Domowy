package pl.dev.household.budget.manager.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.dev.household.budget.manager.dao.Goals;

import java.util.List;
import java.util.Optional;

public interface GoalsRepository extends JpaRepository<Goals, Integer> {

    public Optional<List<Goals>> findAllByHousehold_Id(Integer householdId);

}
