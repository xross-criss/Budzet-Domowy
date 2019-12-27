package pl.dev.household.budget.manager.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.dev.household.budget.manager.dao.repository.BalanceRepository;
import pl.dev.household.budget.manager.domain.Balance;
import pl.dev.household.budget.manager.utils.BalanceUtils;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class BalanceService {

    BalanceRepository balanceRepository;

    @Autowired
    public BalanceService(BalanceRepository balanceRepository) {
        this.balanceRepository = balanceRepository;
    }

    public List<Balance> getBalancesForHousehold(Integer householdId) {
        return balanceRepository.findAllByHousehold_Id(householdId).stream()
                .map(balance ->
                        BalanceUtils.convertToDTO(
                                java.util.Optional.ofNullable(balance)
                        )
                ).collect(Collectors.toList());
    }

    public List<Balance> generateAndReturnBalances(Integer householdId) {
        //TODO
        return null;
    }

    public List<Balance> generate(Integer householdId) {
        //TODO
        return null;
    }
}
