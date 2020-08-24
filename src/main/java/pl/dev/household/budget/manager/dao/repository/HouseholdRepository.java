package pl.dev.household.budget.manager.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.dev.household.budget.manager.dao.Household;

import java.util.Optional;

public interface HouseholdRepository extends JpaRepository<Household, Integer> {

    public Optional<Household> findById(Integer householdId);

}
