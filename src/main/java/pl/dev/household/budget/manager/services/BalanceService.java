package pl.dev.household.budget.manager.services;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.dev.household.budget.manager.dao.repository.BalanceRepository;
import pl.dev.household.budget.manager.dictionaries.BalanceType;
import pl.dev.household.budget.manager.domain.Balance;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class BalanceService {

    private ModelMapper modelMapper;
    private BalanceRepository balanceRepository;

    @Autowired
    public BalanceService(BalanceRepository balanceRepository) {
        this.balanceRepository = balanceRepository;
    }

    public List<Balance> getBalancesForHousehold(Integer householdId) {
        return balanceRepository.findAllByHousehold_Id(householdId).stream()
                .map(balance ->
                        modelMapper.map(balance, Balance.class)
                ).collect(Collectors.toList());
    }

    public List<Balance> generateAndReturnBalances(Integer householdId) {
        //TODO
        return null;
    }

    public List<Balance> generate(Integer householdId, BalanceType type) {
        //TODO
        return null;
    }
}
