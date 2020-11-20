package pl.dev.household.budget.manager.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.dev.household.budget.manager.dao.DebtCard;

import java.util.List;
import java.util.Optional;

public interface DebtCardRepository extends JpaRepository<DebtCard, Integer> {

    Optional<List<DebtCard>> findAllByHousehold_Id(Integer householdId);

}
