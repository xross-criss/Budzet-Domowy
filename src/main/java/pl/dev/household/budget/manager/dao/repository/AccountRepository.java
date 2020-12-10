package pl.dev.household.budget.manager.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.dev.household.budget.manager.dao.Account;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Integer> {

    public Optional<List<Account>> findAllByUserId(Integer userId);

    public void deleteById(Integer accountId);

    public Optional<Account> findByIdAndUserId(Integer accountId, Integer userId);
}
