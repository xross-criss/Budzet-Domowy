package pl.dev.household.budget.manager.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.dev.household.budget.manager.dao.InsuranceDAO;

import java.util.List;

public interface InsuranceRepository extends JpaRepository<InsuranceDAO, Integer> {

    public List<InsuranceDAO> findAllByHousehold_Id(Integer householdId);

}
