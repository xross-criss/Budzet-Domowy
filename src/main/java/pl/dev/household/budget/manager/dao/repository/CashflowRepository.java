package pl.dev.household.budget.manager.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.dev.household.budget.manager.dao.CashflowDAO;

import java.util.List;

public interface CashflowRepository extends JpaRepository<CashflowDAO, Integer> {

    public List<CashflowDAO> findAllByHousehold_Id(Integer householdId);

}
