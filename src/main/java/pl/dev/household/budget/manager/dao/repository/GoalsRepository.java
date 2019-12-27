package pl.dev.household.budget.manager.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.dev.household.budget.manager.dao.GoalsDAO;

import java.util.List;

public interface GoalsRepository extends JpaRepository<GoalsDAO, Integer> {

    public List<GoalsDAO> findAllByHousehold_Id(Integer householdId);

}
