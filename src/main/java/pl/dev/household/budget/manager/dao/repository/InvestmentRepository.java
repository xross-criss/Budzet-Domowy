package pl.dev.household.budget.manager.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.dev.household.budget.manager.dao.Investment;

import java.util.List;
import java.util.Optional;

public interface InvestmentRepository extends JpaRepository<Investment, Integer> {

    public Optional<List<Investment>> findAllByUserId(Integer userId);

}
