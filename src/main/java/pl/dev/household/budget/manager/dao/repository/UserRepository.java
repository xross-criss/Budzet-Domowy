package pl.dev.household.budget.manager.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.dev.household.budget.manager.dao.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {

    List<User> findAllByHousehold_Id(Integer householdId);
    User findOneByLogin(String login);

}
