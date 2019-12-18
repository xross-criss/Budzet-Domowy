package pl.dev.household.budget.manager.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.dev.household.budget.manager.dao.GoalsDAO;

public interface GoalsRepository extends JpaRepository<GoalsDAO, Integer> {
}
