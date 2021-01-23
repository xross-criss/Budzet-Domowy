package pl.dev.household.budget.manager.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.dev.household.budget.manager.dao.Balance;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface BalanceRepository extends JpaRepository<Balance, Integer> {

    public Optional<List<Balance>> findAllByHousehold_Id(Integer householdId);

    public Optional<Balance> findById(@NotNull Integer balanceId);

    public Optional<List<Balance>> findByHouseholdIdAndGenerationDateIsGreaterThanEqualAndGenerationDateIsLessThanEqual(
            Integer householdId,
            LocalDate generationDate_dateStart,
            LocalDate generationDate_dateEnd
    );

}
