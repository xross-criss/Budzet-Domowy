package pl.dev.household.budget.manager.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.dev.household.budget.manager.dao.Cashflow;

import java.util.List;
import java.util.Optional;

public interface CashflowRepository extends JpaRepository<Cashflow, Integer> {

    public Optional<List<Cashflow>> findAllByUserId(Integer userId);

}
