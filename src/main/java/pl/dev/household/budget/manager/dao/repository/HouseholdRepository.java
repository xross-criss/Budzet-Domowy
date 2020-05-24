package pl.dev.household.budget.manager.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.dev.household.budget.manager.dao.Household;

public interface HouseholdRepository extends JpaRepository<Household, Integer> {
}
