package pl.dev.household.budget.manager.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.dev.household.budget.manager.dao.Insurance;

import java.util.List;

public interface InsuranceRepository extends JpaRepository<Insurance, Integer> {

    public List<Insurance> findAllByHousehold_Id(Integer householdId);

}
