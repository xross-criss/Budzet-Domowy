package pl.dev.household.budget.manager.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.dev.household.budget.manager.dao.InvestmentDAO;

import java.util.List;

public interface InvestmentRepository extends JpaRepository<InvestmentDAO, Integer> {

    public List<InvestmentDAO> findAllByHousehold_Id(Integer householdId);

}
