package pl.dev.household.budget.manager.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.dev.household.budget.manager.dao.Balance;

import java.util.List;

public interface BalanceRepository extends JpaRepository<Balance, Integer> {

    public List<Balance> findAllByHousehold_Id(Integer householdId);

}
