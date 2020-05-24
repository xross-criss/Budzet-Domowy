package pl.dev.household.budget.manager.dao.repository.view;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.dev.household.budget.manager.dao.view.VWLoan;

import java.util.List;

public interface VWLoanRepository extends JpaRepository<VWLoan, Integer> {

    public List<VWLoan> findAllByHousehold_Id(Integer householdId);

}
