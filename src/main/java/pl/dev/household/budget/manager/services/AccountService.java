package pl.dev.household.budget.manager.services;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;
import pl.dev.household.budget.manager.dao.Account;
import pl.dev.household.budget.manager.dao.User;
import pl.dev.household.budget.manager.dao.repository.AccountRepository;
import pl.dev.household.budget.manager.dao.repository.UserRepository;
import pl.dev.household.budget.manager.domain.AccountDTO;
import pl.dev.household.budget.manager.domain.ReportIntDTO;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
public class AccountService {

    private ModelMapper modelMapper;
    private AccountRepository accountRepository;
    private UserRepository userRepository;

    public AccountService(ModelMapper modelMapper, AccountRepository accountRepository, UserRepository userRepository) {
        this.modelMapper = modelMapper;
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
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

    public void updateAccount(AccountDTO accountDTO) throws Exception {
        Account accountTmp = accountRepository.findByIdAndUserId(accountDTO.getId(), accountDTO.getUser().getId()).orElse(null);

        if (accountTmp == null || accountTmp.getUser() == null) {
            throw new Exception("Account to be updated not found");
        }

        Account account = modelMapper.map(accountDTO, Account.class);
        account.setUser(accountTmp.getUser());

        accountRepository.save(account);
    }

    public ReportIntDTO accountAccountBalance(Integer householdId) throws Exception {
        List<User> usersInHousehold = userRepository.findAllByHousehold_Id(householdId).orElse(Collections.emptyList());

        if (usersInHousehold.isEmpty()) {
            throw new Exception("Users in the household not found");
        }

        List<Account> accountList = new ArrayList<>();

        usersInHousehold.stream().forEach(user -> {
            List<Account> tmp = accountRepository.findAllByUserId(user.getId()).orElse(Collections.emptyList());
            if (!tmp.isEmpty()) {
                accountList.addAll(tmp);
            }
        });

        ReportIntDTO report = new ReportIntDTO();

        accountList.forEach(account -> {
            report.setIncome(account.getAmount());
        });

        return report;
    }
}
