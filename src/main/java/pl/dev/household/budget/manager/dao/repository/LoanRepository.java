package pl.dev.household.budget.manager.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.dev.household.budget.manager.dao.LoanDAO;

import java.util.List;

public interface LoanRepository extends JpaRepository<LoanDAO, Integer> {

    public List<LoanDAO> findAllByHousehold_Id(Integer householdId);

}
