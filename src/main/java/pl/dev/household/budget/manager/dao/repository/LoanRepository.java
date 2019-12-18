package pl.dev.household.budget.manager.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.dev.household.budget.manager.dao.LoanDAO;

public interface LoanRepository extends JpaRepository<LoanDAO, Integer> {
}
