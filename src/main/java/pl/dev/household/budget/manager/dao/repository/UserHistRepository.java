package pl.dev.household.budget.manager.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.dev.household.budget.manager.dao.UserHist;

import java.util.List;
import java.util.Optional;

public interface UserHistRepository extends JpaRepository<UserHist, Integer> {

    public Optional<List<UserHist>> findAllByUserIdAndOperationLike(Integer userId, String operation);

}
