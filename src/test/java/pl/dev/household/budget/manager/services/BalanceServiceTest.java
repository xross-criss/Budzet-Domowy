package pl.dev.household.budget.manager.services;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import pl.dev.household.budget.manager.dao.Balance;
import pl.dev.household.budget.manager.dao.Household;
import pl.dev.household.budget.manager.dao.repository.BalanceRepository;
import pl.dev.household.budget.manager.dao.repository.HouseholdRepository;
import pl.dev.household.budget.manager.dictionaries.BalanceMapType;
import pl.dev.household.budget.manager.dictionaries.BalanceType;
import pl.dev.household.budget.manager.domain.BalanceDTO;
import pl.dev.household.budget.manager.domain.HouseholdDTO;
import pl.dev.household.budget.manager.utils.MockDataUtil;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BalanceServiceTest {

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private HouseholdRepository householdRepository;

    @Mock
    private BalanceRepository balanceRepository;

    @Mock
    private CashflowService cashflowService;

    @Mock
    private DebtCardService debtCardService;

    @Mock
    private InsuranceService insuranceService;

    @Mock
    private InvestmentService investmentService;

    @Mock
    private LoanService loanService;

    @InjectMocks
    private BalanceService balanceService;

    Household household;
    Balance previousMonthBalance;

    @Before
    public void setup() {

    }

    @Test
    public void resolveCashflowBalance_givenServices_shouldFetchDataAndReturn() throws Exception {
        //given
        household = MockDataUtil.initHousehold();
        previousMonthBalance = MockDataUtil.initPreviousMonthBalance(household);
        HouseholdDTO householdDTO = MockDataUtil.mapHouseholdToHouseholdDTO(household);
        BalanceDTO previousMonthBalanceDTO = MockDataUtil.mapBalanceToBalanceDTOinitPreviousMonthBalance(previousMonthBalance);

        //when
        when(cashflowService.countCashflowBalance(any())).thenReturn(MockDataUtil.countCashflowBalanceMock());
        when(debtCardService.countDebtCardBalance(any())).thenReturn(MockDataUtil.countDebtCardBalanceMock());
        when(insuranceService.countInsuranceBalance(any())).thenReturn(MockDataUtil.countInsuranceBalanceMock());
        when(investmentService.countInvestmentBalance(any())).thenReturn(MockDataUtil.countInvestmentBalanceMock());
        when(loanService.countLoansBalance(any())).thenReturn(MockDataUtil.countLoanBalanceMock());

        HashMap<BalanceMapType, BigDecimal> result = balanceService.resolveCashflowBalance(householdDTO, previousMonthBalanceDTO);

        //then
        assertThat(result).isNotNull();
        assertThat(result.get(BalanceMapType.BALANCE)).isEqualTo(BigDecimal.valueOf(0));
        assertThat(result.get(BalanceMapType.INCOME)).isEqualTo(BigDecimal.valueOf(4000));
        assertThat(result.get(BalanceMapType.BURDEN)).isEqualTo(BigDecimal.valueOf(4000));
    }
}
