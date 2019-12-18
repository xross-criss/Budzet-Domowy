package pl.dev.household.budget.manager.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.dev.household.budget.manager.dao.BalanceDAO;

public interface BalanceRepository extends JpaRepository<BalanceDAO, Integer> {
}
