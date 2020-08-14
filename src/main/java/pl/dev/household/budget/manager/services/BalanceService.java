package pl.dev.household.budget.manager.services;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import pl.dev.household.budget.manager.dao.Balance;
import pl.dev.household.budget.manager.dao.Loan;
import pl.dev.household.budget.manager.dao.repository.BalanceRepository;
import pl.dev.household.budget.manager.dao.repository.HouseholdRepository;
import pl.dev.household.budget.manager.dictionaries.BalanceMapType;
import pl.dev.household.budget.manager.dictionaries.BalanceType;
import pl.dev.household.budget.manager.domain.BalanceDTO;
import pl.dev.household.budget.manager.domain.HouseholdDTO;
import pl.dev.household.budget.manager.domain.ReportIntDTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class BalanceService {

    private ModelMapper modelMapper;
    private BalanceRepository balanceRepository;
    private HouseholdRepository householdRepository;

    public BalanceService(ModelMapper modelMapper, BalanceRepository balanceRepository, CashflowRepository cashflowRepository, HouseholdRepository householdRepository) {
        this.modelMapper = modelMapper;
        this.balanceRepository = balanceRepository;
        this.cashflowRepository = cashflowRepository;
        this.householdRepository = householdRepository;
    }

    public List<BalanceDTO> getBalancesForHousehold(Integer householdId) {
        return balanceRepository.findAllByHousehold_Id(householdId).stream()
                .map(balance ->
                        modelMapper.map(balance, BalanceDTO.class)
                ).collect(Collectors.toList());
    }

    public BalanceDTO getBalanceById(Integer balanceId) {
        return balanceRepository.findById(balanceId).map(balance ->
                modelMapper.map(balance, BalanceDTO.class)
        ).get();
    }

    public BalanceDTO generateAndReturnBalance(Integer householdId) {
        return generate(householdId, BalanceType.GENERATED);
    }

    public BalanceDTO generate(Integer householdId, BalanceType type) {
        LocalDate date = LocalDate.now();

        HouseholdDTO householdDTO = householdRepository.findById(householdId).map(household -> modelMapper.map(household, HouseholdDTO.class)).get();

/*        if (type.equals(BalanceType.SUMMARY)) {
            date = date.minusMonths(1);
        }*/

        return aggregateAndGenerate(householdDTO, type, date);
    }

    private BalanceDTO aggregateAndGenerate(HouseholdDTO householdDTO, BalanceType type, LocalDate date) {
        BalanceDTO previousMonthBalance = getSummaryBalanceByMonth(householdDTO.getId(), date.minusMonths(1));
        checkIfBalanceExists(previousMonthBalance);

        HashMap<BalanceMapType, BigDecimal> resolvedCashflowBalance = resolveCashflowBalance(
                householdDTO,
                cashflowRepository.findAllByHousehold_Id(householdDTO.getId()).stream()
                        .filter(checkIfMonthIsPeriodic())
                        .collect(Collectors.toList()),
                previousMonthBalance
        );

        Balance finalBalance = new Balance();
        finalBalance.setHousehold(householdRepository.findById(householdDTO.getId()).get());
        finalBalance.setType(type);
        finalBalance.setGenerationDate(LocalDate.now());
        finalBalance.setBurden(resolvedCashflowBalance.get(BalanceMapType.BURDEN));
        finalBalance.setIncome(resolvedCashflowBalance.get(BalanceMapType.INCOME));
        finalBalance.setBalance(resolvedCashflowBalance.get(BalanceMapType.BALANCE));

        finalBalance = balanceRepository.save(finalBalance);
        return modelMapper.map(finalBalance, BalanceDTO.class);
    }

    private HashMap<BalanceMapType, BigDecimal> resolveCashflowBalance(HouseholdDTO householdDTO, List<Cashflow> cashflowList, BalanceDTO previousMonthBalance) {
        BigDecimal balanceResult = previousMonthBalance.getBalance();
        BigDecimal income = BigDecimal.valueOf(0);
        BigDecimal burden = householdDTO.getCost(); // koszty podstawowe gospodarstwa domowego

        for (Cashflow cashflow : cashflowList) {
            if (cashflow.getCategory().equals(CashflowCategory.INCOME)) {
                income = income.add(cashflow.getAmount());
            } else {
                burden = burden.add(cashflow.getAmount());
            }
        }

        balanceResult = balanceResult.add(income);
        balanceResult = balanceResult.subtract(burden);

        HashMap<BalanceMapType, BigDecimal> balanceMap = new HashMap<>();
        balanceMap.put(BalanceMapType.BALANCE, balanceResult);
        balanceMap.put(BalanceMapType.INCOME, income);
        balanceMap.put(BalanceMapType.BURDEN, burden);

        return balanceMap;
    }

    private void checkIfBalanceExists(BalanceDTO previousMonthBalance) {
        if (previousMonthBalance == null) {
            previousMonthBalance.setBalance(BigDecimal.valueOf(0));
            previousMonthBalance.setBurden(BigDecimal.valueOf(0));
            previousMonthBalance.setIncome(BigDecimal.valueOf(0));
        }
    }

    private BalanceDTO getSummaryBalanceByMonth(Integer householdId, LocalDate month) {
        return balanceRepository.findByHouseholdIdAndGenerationDateBetween(
                householdId,
                month.minusMonths(1).plusDays(1),
                month.plusMonths(1).minusDays(1)
        ).stream()
                .map(balance -> modelMapper.map(balance, BalanceDTO.class))
                .collect(Collectors.toList())
                .get(0);
    }

    public static Predicate<Cashflow> checkIfMonthIsPeriodic() {
        return p -> Period.between(p.getStartDate(), LocalDate.now()).getMonths() % p.getInterval() == 0;
    }

}
