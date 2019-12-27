package pl.dev.household.budget.manager.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.dev.household.budget.manager.dao.BalanceDAO;

import java.util.List;

public interface BalanceRepository extends JpaRepository<BalanceDAO, Integer> {

    public List<BalanceDAO> findAllByHousehold_Id(Integer householdId);

}
