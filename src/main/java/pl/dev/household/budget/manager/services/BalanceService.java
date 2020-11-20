package pl.dev.household.budget.manager.services;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import pl.dev.household.budget.manager.dao.Balance;
import pl.dev.household.budget.manager.dao.repository.BalanceRepository;
import pl.dev.household.budget.manager.dao.repository.HouseholdRepository;
import pl.dev.household.budget.manager.dictionaries.BalanceMapType;
import pl.dev.household.budget.manager.dictionaries.BalanceType;
import pl.dev.household.budget.manager.domain.BalanceDTO;
import pl.dev.household.budget.manager.domain.HouseholdDTO;
import pl.dev.household.budget.manager.domain.ReportIntDTO;
import pl.dev.household.budget.manager.utils.HouseholdMapper;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class BalanceService {

    private ModelMapper modelMapper;
    private BalanceRepository balanceRepository;
    private HouseholdRepository householdRepository;
    private CashflowService cashflowService;
    private DebtCardService debtCardService;
    private LoanService loanService;
    private InsuranceService insuranceService;
    private InvestmentService investmentService;

    public BalanceService(
            ModelMapper modelMapper,
            BalanceRepository balanceRepository,
            HouseholdRepository householdRepository,
            CashflowService cashflowService,
            DebtCardService debtCardService,
            LoanService loanService,
            InsuranceService insuranceService,
            InvestmentService investmentService
    ) {
        this.modelMapper = modelMapper;
        this.balanceRepository = balanceRepository;
        this.householdRepository = householdRepository;
        this.cashflowService = cashflowService;
        this.debtCardService = debtCardService;
        this.loanService = loanService;
        this.insuranceService = insuranceService;
        this.investmentService = investmentService;
    }

    public List<BalanceDTO> getBalancesForHousehold(Integer householdId) {
        Optional<List<Balance>> optList = Optional.of(balanceRepository.findAllByHousehold_Id(householdId).orElse(Collections.emptyList()));

        return optList.stream()
                .flatMap(Collection::stream)
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
        return generateAndSave(householdId, BalanceType.GENERATED);
    }

    public BalanceDTO generateAndSave(Integer householdId, BalanceType type) {
        LocalDate date = LocalDate.now();

        HouseholdDTO householdDTO = householdRepository.findById(householdId).map(household -> modelMapper.map(household, HouseholdDTO.class)).get();

/*        if (type.equals(BalanceType.SUMMARY)) {
            date = date.minusMonths(1);
        }*/

        BalanceDTO balanceDTO = aggregateAndGenerate(householdDTO, type, date);
        balanceDTO = modelMapper.map(balanceRepository.save(modelMapper.map(balanceDTO, Balance.class)), BalanceDTO.class);
        return balanceDTO;
    }

    public BalanceDTO aggregateAndGenerate(Integer householdId, BalanceType type, LocalDate date) {
        HouseholdDTO householdDTO = HouseholdMapper.mapHousehold(householdRepository.findById(householdId).get());

        return aggregateAndGenerate(householdDTO, type, date);
    }

    private BalanceDTO aggregateAndGenerate(HouseholdDTO householdDTO, BalanceType type, LocalDate date) {
        BalanceDTO previousMonthBalance = getSummaryBalanceByMonth(householdDTO.getId(), date.minusMonths(1));
        checkIfBalanceExists(previousMonthBalance);

        HashMap<BalanceMapType, BigDecimal> resolvedCashflowBalance = resolveCashflowBalance(
                householdDTO,
                previousMonthBalance
        );

        Balance finalBalance = new Balance();
        finalBalance.setHousehold(householdRepository.findById(householdDTO.getId()).get());
        finalBalance.setType(type);
        finalBalance.setGenerationDate(LocalDate.now());
        finalBalance.setBurden(resolvedCashflowBalance.get(BalanceMapType.BURDEN));
        finalBalance.setIncome(resolvedCashflowBalance.get(BalanceMapType.INCOME));
        finalBalance.setBalance(resolvedCashflowBalance.get(BalanceMapType.BALANCE));

        return modelMapper.map(finalBalance, BalanceDTO.class);
    }

    private HashMap<BalanceMapType, BigDecimal> resolveCashflowBalance(HouseholdDTO householdDTO, BalanceDTO previousMonthBalance) {

        BigDecimal balanceResult = previousMonthBalance.getBalance();
        BigDecimal income = BigDecimal.valueOf(0);
        BigDecimal burden = householdDTO.getCost(); // koszty podstawowe gospodarstwa domowego

        ReportIntDTO cashflowReport = cashflowService.countCashflowBalance(householdDTO.getId());
        ReportIntDTO debtCardsReport = debtCardService.countDebtCardBalance(householdDTO.getId());
        ReportIntDTO insurancesReport = insuranceService.countInsuranceBalance(householdDTO.getId());
        ReportIntDTO investmentsReport = investmentService.countInvestmentBalance(householdDTO.getId());
        ReportIntDTO loansReport = loanService.countLoansBalance(householdDTO.getId());

        income = income.add(cashflowReport.getIncome());
        burden = burden.add(cashflowReport.getBurden());
        burden = burden.add(debtCardsReport.getBurden());
        burden = burden.add(insurancesReport.getBurden());
        income = income.add(investmentsReport.getIncome());
        burden = burden.add(loansReport.getBurden());


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
        Optional<List<Balance>> byHouseholdIdAndGenerationDateBetween = balanceRepository.findByHouseholdIdAndGenerationDateBetween(
                householdId,
                month.minusMonths(1).plusDays(1),
                month.plusMonths(1).minusDays(1)
        );

        if (byHouseholdIdAndGenerationDateBetween.isEmpty()) {
            return null;
        }
        return modelMapper.map(byHouseholdIdAndGenerationDateBetween.get().get(0), BalanceDTO.class);
    }

    public List<BalanceDTO> getBalancesForHouseholdNoMonthAgo(Integer householdId, int no) {
        LocalDate startDate = LocalDate.now().minusMonths(no).withDayOfMonth(1).minusDays(1);
        LocalDate endDate = LocalDate.now().minusMonths(no).withDayOfMonth(1).plusMonths(1);

        Optional<List<Balance>> optList = Optional.of(
                balanceRepository.findByHouseholdIdAndGenerationDateBetween(
                        householdId,
                        startDate,
                        endDate
                ).orElse(Collections.emptyList()));

        return sortBalanceList(optList.stream()
                .flatMap(Collection::stream)
                .map(balance -> modelMapper.map(balance, BalanceDTO.class))
                .collect(Collectors.toList()));

    }

    private List<BalanceDTO> sortBalanceList(List<BalanceDTO> balanceDTOList) {
        Map<BalanceType, List<BalanceDTO>> sortMap = new HashMap<>();
        sortMap.put(BalanceType.PREDICTION, new ArrayList<>());
        sortMap.put(BalanceType.GENERATED, new ArrayList<>());
        sortMap.put(BalanceType.SUMMARY, new ArrayList<>());

        for (BalanceDTO balanceDTO : balanceDTOList) {
            if (balanceDTO.getType().equals(BalanceType.PREDICTION)) {
                List<BalanceDTO> tmp = sortMap.get(BalanceType.PREDICTION);
                tmp.add(balanceDTO);
                sortMap.replace(BalanceType.PREDICTION, tmp);
            } else if (balanceDTO.getType().equals(BalanceType.GENERATED)) {
                List<BalanceDTO> tmp = sortMap.get(BalanceType.GENERATED);
                tmp.add(balanceDTO);
                sortMap.replace(BalanceType.GENERATED, tmp);
            } else {
                List<BalanceDTO> tmp = sortMap.get(BalanceType.SUMMARY);
                tmp.add(balanceDTO);
                sortMap.replace(BalanceType.SUMMARY, tmp);
            }
        }

        List<BalanceDTO> tmp = sortMap.get(BalanceType.GENERATED);
        tmp.sort(Comparator.comparing(BalanceDTO::getGenerationDate));
        sortMap.replace(BalanceType.GENERATED, tmp);

        List<BalanceDTO> list = new ArrayList<>();
        list.addAll(sortMap.get(BalanceType.PREDICTION));
        list.addAll(sortMap.get(BalanceType.GENERATED));
        list.addAll(sortMap.get(BalanceType.SUMMARY));

        return list;
    }
}
