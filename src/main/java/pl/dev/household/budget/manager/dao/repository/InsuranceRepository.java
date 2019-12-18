package pl.dev.household.budget.manager.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.dev.household.budget.manager.dao.InsuranceDAO;

public interface InsuranceRepository extends JpaRepository<InsuranceDAO, Integer> {
}
