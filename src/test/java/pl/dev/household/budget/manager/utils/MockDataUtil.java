package pl.dev.household.budget.manager.utils;

import pl.dev.household.budget.manager.dao.Balance;
import pl.dev.household.budget.manager.dao.Household;
import pl.dev.household.budget.manager.dao.User;
import pl.dev.household.budget.manager.dictionaries.BalanceType;
import pl.dev.household.budget.manager.dictionaries.UserRole;
import pl.dev.household.budget.manager.domain.BalanceDTO;
import pl.dev.household.budget.manager.domain.HouseholdDTO;
import pl.dev.household.budget.manager.domain.ReportIntDTO;
import pl.dev.household.budget.manager.domain.UserDTO;

import java.math.BigDecimal;
import java.time.LocalDate;

public class MockDataUtil {

    public static Household initHousehold() {
        Household household = new Household();

        household.setId(1);
        household.setPopulation(1);
        household.setCost(BigDecimal.valueOf(0));

        return household;
    }

    public static Balance initPreviousMonthBalance(Household household) {
        Balance balance = new Balance();
        balance.setId(1);
        balance.setHousehold(household);
        balance.setType(BalanceType.SUMMARY);
        balance.setGenerationDate(LocalDate.now().minusMonths(1).withDayOfMonth(LocalDate.now().minusMonths(1).lengthOfMonth()));
        balance.setBurden(BigDecimal.valueOf(4000));
        balance.setIncome(BigDecimal.valueOf(4000));
        balance.setBalance(BigDecimal.valueOf(0));

        return balance;
    }

    public static BalanceDTO mapBalanceToBalanceDTOinitPreviousMonthBalance(Balance balance) {
        BalanceDTO balanceDTO = new BalanceDTO();

        balanceDTO.setId(balance.getId());
        balanceDTO.setHousehold(mapHouseholdToHouseholdDTO(balance.getHousehold()));
        balanceDTO.setType(balance.getType());
        balanceDTO.setGenerationDate(balance.getGenerationDate());
        balanceDTO.setBurden(balance.getBurden());
        balanceDTO.setIncome(balance.getIncome());
        balanceDTO.setBalance(balance.getBalance());

        return balanceDTO;

    }

    public static HouseholdDTO mapHouseholdToHouseholdDTO(Household household) {
        HouseholdDTO householdDTO = new HouseholdDTO();

        householdDTO.setId(household.getId());
        householdDTO.setPopulation(household.getPopulation());
        householdDTO.setCost(household.getCost());

        return householdDTO;
    }

    public static ReportIntDTO countCashflowBalanceMock() {
        ReportIntDTO reportIntDTO = new ReportIntDTO();

        reportIntDTO.setIncome(BigDecimal.valueOf(2000));
        reportIntDTO.setBurden(BigDecimal.valueOf(1000));
        reportIntDTO.setAmount(BigDecimal.valueOf(0));

        return reportIntDTO;
    }

    public static ReportIntDTO countDebtCardBalanceMock() {
        ReportIntDTO reportIntDTO = new ReportIntDTO();

        reportIntDTO.setIncome(BigDecimal.valueOf(0));
        reportIntDTO.setBurden(BigDecimal.valueOf(1000));
        reportIntDTO.setAmount(BigDecimal.valueOf(0));

        return reportIntDTO;
    }

    public static ReportIntDTO countInsuranceBalanceMock() {
        ReportIntDTO reportIntDTO = new ReportIntDTO();

        reportIntDTO.setIncome(BigDecimal.valueOf(0));
        reportIntDTO.setBurden(BigDecimal.valueOf(1000));
        reportIntDTO.setAmount(BigDecimal.valueOf(0));

        return reportIntDTO;
    }

    public static ReportIntDTO countInvestmentBalanceMock() {
        ReportIntDTO reportIntDTO = new ReportIntDTO();

        reportIntDTO.setIncome(BigDecimal.valueOf(2000));
        reportIntDTO.setBurden(BigDecimal.valueOf(0));
        reportIntDTO.setAmount(BigDecimal.valueOf(0));

        return reportIntDTO;
    }

    public static ReportIntDTO countLoanBalanceMock() {
        ReportIntDTO reportIntDTO = new ReportIntDTO();

        reportIntDTO.setIncome(BigDecimal.valueOf(0));
        reportIntDTO.setBurden(BigDecimal.valueOf(1000));
        reportIntDTO.setAmount(BigDecimal.valueOf(0));

        return reportIntDTO;
    }

    public static User initUserMock(Household household) {
        User user = new User();

        user.setId(1);
        user.setHousehold(household);
        user.setUserRole(UserRole.FAMILY_GUY);
        user.setLogin("test");
        user.setPassword("test");
        user.setName("test");
        user.setEmail("test");
        user.setRegistrationDate(LocalDate.of(2000, 1, 1));
        user.setLastFailedLogin(LocalDate.of(2525, 1, 1));

        return user;
    }

    public static UserDTO mapUserToUserDTO(User user) {
        UserDTO userDTO = new UserDTO();

        userDTO.setId(user.getId());
        userDTO.setHousehold(mapHouseholdToHouseholdDTO(user.getHousehold()));
        userDTO.setUserRole(user.getUserRole());
        userDTO.setLogin(user.getLogin());
        userDTO.setPassword(user.getPassword());
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
        userDTO.setRegistrationDate(user.getRegistrationDate());
        userDTO.setLastFailedLogin(user.getLastFailedLogin());

        return userDTO;
    }

}
