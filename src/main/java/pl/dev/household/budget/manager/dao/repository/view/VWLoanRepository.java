package pl.dev.household.budget.manager.dao.repository.view;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.dev.household.budget.manager.dao.view.VWLoanDAO;

import java.util.List;

public interface VWLoanRepository extends JpaRepository<VWLoanDAO, Integer> {

    public List<VWLoanDAO> findAllByHousehold_Id(Integer householdId);

}
