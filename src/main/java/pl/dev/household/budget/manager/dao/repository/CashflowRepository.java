package pl.dev.household.budget.manager.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.dev.household.budget.manager.dao.CashflowDAO;

public interface CashflowRepository extends JpaRepository<CashflowDAO, Integer> {
}
