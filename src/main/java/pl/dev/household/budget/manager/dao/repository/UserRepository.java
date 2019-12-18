package pl.dev.household.budget.manager.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.dev.household.budget.manager.dao.UserDAO;

public interface UserRepository extends JpaRepository<UserDAO, Integer> {
}
