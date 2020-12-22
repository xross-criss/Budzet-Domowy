package pl.dev.household.budget.manager.services;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;
import pl.dev.household.budget.manager.dao.Bank;
import pl.dev.household.budget.manager.dao.repository.BankRepository;
import pl.dev.household.budget.manager.domain.AccountDTO;
import pl.dev.household.budget.manager.domain.BankDTO;

import java.util.Collections;
import java.util.List;

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

    public BankDTO saveBank(BankDTO dto) {
        return modelMapper.map(bankRepository.save(modelMapper.map(dto, Bank.class)), BankDTO.class);
    }

    public void deleteBank(Integer bankId) {
        bankRepository.deleteById(bankId);
    }

    public void updateBank(BankDTO bankDTO) throws Exception {
        Bank bankTmp = bankRepository.findBankById(bankDTO.getId()).orElse(null);

        if (bankTmp == null) {
            throw new Exception("No Bank to be updated found!");
        }

        Bank bank = modelMapper.map(bankDTO, Bank.class);
        bank.setHousehold(bankTmp.getHousehold());

        bankRepository.save(bank);
    }

    public List<BankDTO> getBankList(Integer householdId) {
        return modelMapper.map(bankRepository.findBanksByHouseholdId(householdId).orElse(Collections.emptyList()), new TypeToken<List<BankDTO>>() {
        }.getType());
    }
}
