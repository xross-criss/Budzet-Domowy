package pl.dev.household.budget.manager.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.dev.household.budget.manager.dao.Balance;
import pl.dev.household.budget.manager.dao.Cashflow;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CashflowRepository extends JpaRepository<Cashflow, Integer> {

    public Optional<List<Cashflow>> findAllByUserIdAndPeriodNotLike(Integer userId, Integer period);

    public Optional<List<Cashflow>> findAllByUserIdAndStartDateBetweenAndPeriodLike(Integer userId, LocalDate startDate, LocalDate endDate, Integer period);
}
