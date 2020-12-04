package pl.dev.household.budget.manager.services;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import pl.dev.household.budget.manager.dao.Bank;
import pl.dev.household.budget.manager.dao.repository.BankRepository;
import pl.dev.household.budget.manager.domain.BankDTO;

@Slf4j
@Service
public class BankService {

    private ModelMapper modelMapper;
    private BankRepository bankRepository;

    public BankService(ModelMapper modelMapper, BankRepository bankRepository) {
        this.modelMapper = modelMapper;
        this.bankRepository = bankRepository;
    }

    public BankDTO getBankDTOById(Integer bankId) {
        return modelMapper.map(getBankById(bankId), BankDTO.class);
    }

    public Bank getBankById(Integer bankId) {
        return bankRepository.findBankById(bankId).orElse(null);
    }

}
