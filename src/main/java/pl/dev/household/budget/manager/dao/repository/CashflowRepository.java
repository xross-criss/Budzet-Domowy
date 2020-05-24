package pl.dev.household.budget.manager.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.dev.household.budget.manager.dao.Cashflow;

import java.util.List;

public interface CashflowRepository extends JpaRepository<Cashflow, Integer> {

    public List<Cashflow> findAllByHousehold_Id(Integer householdId);

}
