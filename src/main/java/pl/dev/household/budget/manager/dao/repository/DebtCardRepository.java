package pl.dev.household.budget.manager.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.dev.household.budget.manager.dao.DebtCardDAO;

import java.util.List;

public interface DebtCardRepository extends JpaRepository<DebtCardDAO, Integer> {

    List<DebtCardDAO> findAllByHousehold_Id(Integer householdId);

}
