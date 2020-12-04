package pl.dev.household.budget.manager.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.dev.household.budget.manager.dao.Bank;

import java.util.Optional;

public interface BankRepository extends JpaRepository<Bank, Integer> {

    public Optional<Bank> findBankById(Integer bankId);

}
