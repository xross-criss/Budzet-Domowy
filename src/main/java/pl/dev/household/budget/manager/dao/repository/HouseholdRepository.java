package pl.dev.household.budget.manager.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.dev.household.budget.manager.dao.HouseholdDAO;

public interface HouseholdRepository extends JpaRepository<HouseholdDAO, Integer> {
}
