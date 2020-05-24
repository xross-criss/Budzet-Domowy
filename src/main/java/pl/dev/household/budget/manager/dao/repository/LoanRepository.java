package pl.dev.household.budget.manager.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.dev.household.budget.manager.dao.Loan;

import java.util.List;

public interface LoanRepository extends JpaRepository<Loan, Integer> {

    public List<Loan> findAllByHousehold_Id(Integer householdId);

}
