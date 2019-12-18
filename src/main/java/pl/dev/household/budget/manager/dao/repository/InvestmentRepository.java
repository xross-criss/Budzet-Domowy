package pl.dev.household.budget.manager.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.dev.household.budget.manager.dao.InvestmentDAO;

public interface InvestmentRepository extends JpaRepository<InvestmentDAO, Integer> {
}
