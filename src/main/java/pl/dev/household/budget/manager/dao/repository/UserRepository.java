package pl.dev.household.budget.manager.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.dev.household.budget.manager.dao.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<List<User>> findAllByHousehold_Id(Integer householdId);

    Optional<User> findOneByLogin(String login);

//    @Query("dssds")
//    Optional<String> getPassword(String login);

//    @Query("dssds")
//    void setPassword(String login);

}
