package pl.dev.household.budget.manager.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.dev.household.budget.manager.dao.Balance;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface BalanceRepository extends JpaRepository<Balance, Integer> {

    public List<Balance> findAllByHousehold_Id(Integer householdId);

    public Optional<Balance> findById(@NotNull Integer balanceId);

    public List<Balance> findByHouseholdIdAndGenerationDateBetween(Integer householdId, LocalDate generationDate_dateStart, LocalDate generationDate_dateEnd);

}
