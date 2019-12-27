package pl.dev.household.budget.manager.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.dev.household.budget.manager.dao.UserDAO;

import java.util.List;

public interface UserRepository extends JpaRepository<UserDAO, Integer> {

    public List<UserDAO> findAllByHousehold_Id(Integer householdId);

}
