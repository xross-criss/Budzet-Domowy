package pl.dev.household.budget.manager.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.dev.household.budget.manager.dao.Investment;

import java.util.List;

public interface InvestmentRepository extends JpaRepository<Investment, Integer> {

    public List<Investment> findAllByHousehold_Id(Integer householdId);

}
