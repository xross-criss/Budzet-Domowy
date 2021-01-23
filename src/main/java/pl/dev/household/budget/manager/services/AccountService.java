package pl.dev.household.budget.manager.services;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;
import pl.dev.household.budget.manager.dao.Account;
import pl.dev.household.budget.manager.dao.User;
import pl.dev.household.budget.manager.dao.repository.AccountRepository;
import pl.dev.household.budget.manager.domain.AccountDTO;
import pl.dev.household.budget.manager.domain.ReportIntDTO;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
public class AccountService {

    private ModelMapper modelMapper;
    private AccountRepository accountRepository;
    private UserService userService;

    public AccountService(ModelMapper modelMapper, AccountRepository accountRepository, UserService userService) {
        this.modelMapper = modelMapper;
        this.accountRepository = accountRepository;
        this.userService = userService;
    }

    public List<Account> getAccountsForUser(Integer userId) {
        return accountRepository.findAllByUserId(userId).orElse(Collections.emptyList());
    }

    public List<AccountDTO> getAccountsDTOForUser(Integer userId) {
        return modelMapper.map(getAccountsForUser(userId), new TypeToken<List<AccountDTO>>() {
        }.getType());

/*        return accountList.stream().map(account -> {
            AccountDTO accountDTO = modelMapper.map(account, AccountDTO.class);
            accountDTO.setUser(modelMapper.map(account.getUser(), UserDTO.class));
            accountDTO.setBank(modelMapper.map(account.getBank(), BankDTO.class));

            return accountDTO;
        }).collect(Collectors.toList());*/
    }

    public AccountDTO addAccount(AccountDTO accountDTO) {
        return modelMapper.map(accountRepository.save(modelMapper.map(accountDTO, Account.class)), AccountDTO.class);
    }

    public void deleteAccount(Integer userId, Integer accountId) throws Exception {
        Account accountTmp = accountRepository.findByIdAndUserId(accountId, userId).orElse(null);

        if (accountTmp == null || accountTmp.getUser() == null) {
            throw new Exception("Account to be updated not found");
        }

        if (!accountTmp.getUser().getId().equals(userId)) {
            throw new Exception("User from the account doesnt match current user. Account cannot be deleted!");
        }

        accountRepository.deleteById(accountId);
    }

    public void updateAccount(AccountDTO accountDTO, User user) throws Exception {
        Account account = modelMapper.map(accountDTO, Account.class);

        if (accountDTO.getId() == null) {
            Account accountTmp = accountRepository.findByIdAndUserId(accountDTO.getId(), accountDTO.getUser().getId()).orElse(null);

            if (accountTmp == null || accountTmp.getUser() == null) {
                throw new Exception("Account to be updated not found");
            }

            account.setUser(accountTmp.getUser());
        } else {
            account.setUser(user);
        }

        accountRepository.save(account);
    }

    public ReportIntDTO accountAccountBalance(Integer householdId) throws Exception {
        List<AccountDTO> accountList = getAccountsInHousehold(householdId);

        ReportIntDTO report = new ReportIntDTO();

        BigDecimal amount = BigDecimal.valueOf(0);

        for (AccountDTO accountDTO : accountList) {
            amount = amount.add(accountDTO.getAmount());
        }

        report.setIncome(amount);

        return report;
    }

    public List<AccountDTO> getAccountsInHousehold(Integer householdId) throws Exception {
        List<User> usersInHousehold = userService.getAllUsersInHouseholdByHousehold(householdId);

        if (usersInHousehold.isEmpty()) {
            throw new Exception("Users in the household not found");
        }

        List<Account> accountList = new ArrayList<>();

        usersInHousehold.forEach(user -> {
            List<Account> tmp = accountRepository.findAllByUserId(user.getId()).orElse(Collections.emptyList());
            if (!tmp.isEmpty()) {
                accountList.addAll(tmp);
            }
        });

        return modelMapper.map(accountList, new TypeToken<List<AccountDTO>>() {
        }.getType());
    }
}
