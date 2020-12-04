package pl.dev.household.budget.manager.services;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import pl.dev.household.budget.manager.dao.Account;
import pl.dev.household.budget.manager.dao.repository.AccountRepository;
import pl.dev.household.budget.manager.domain.AccountDTO;
import pl.dev.household.budget.manager.domain.BankDTO;
import pl.dev.household.budget.manager.domain.UserDTO;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AccountService {

    private ModelMapper modelMapper;
    private AccountRepository accountRepository;

    public AccountService(
            ModelMapper modelMapper,
            AccountRepository accountRepository
    ) {
        this.modelMapper = modelMapper;
        this.accountRepository = accountRepository;
    }

    private List<Account> getAccountForUser(Integer userId) {
        return accountRepository.findAllByUserId(userId).orElse(Collections.emptyList());
    }

    public List<AccountDTO> getAccountDTOForUser(Integer userId) {
        List<Account> accountList = getAccountForUser(userId);

        return accountList.stream().map(account -> {
            AccountDTO accountDTO = modelMapper.map(account, AccountDTO.class);
            accountDTO.setUser(modelMapper.map(account.getUser(), UserDTO.class));
            accountDTO.setBank(modelMapper.map(account.getBank(), BankDTO.class));

            return accountDTO;
        }).collect(Collectors.toList());
    }

}
