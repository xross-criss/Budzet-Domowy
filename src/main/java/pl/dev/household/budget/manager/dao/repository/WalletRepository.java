package pl.dev.household.budget.manager.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.dev.household.budget.manager.dao.Wallet;

import java.util.Optional;

public interface WalletRepository extends JpaRepository<Wallet, Integer> {

    public Optional<Wallet> findByUserId(Integer userId);

}
