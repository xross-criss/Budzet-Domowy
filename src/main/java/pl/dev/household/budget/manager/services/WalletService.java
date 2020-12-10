package pl.dev.household.budget.manager.services;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import pl.dev.household.budget.manager.dao.User;
import pl.dev.household.budget.manager.dao.Wallet;
import pl.dev.household.budget.manager.dao.repository.UserRepository;
import pl.dev.household.budget.manager.dao.repository.WalletRepository;
import pl.dev.household.budget.manager.domain.WalletDTO;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
public class WalletService {

    private ModelMapper modelMapper;
    private WalletRepository walletRepository;
    private UserRepository userRepository;

    public WalletService(ModelMapper modelMapper, WalletRepository walletRepository, UserRepository userRepository) {
        this.modelMapper = modelMapper;
        this.walletRepository = walletRepository;
        this.userRepository = userRepository;
    }

    public WalletDTO getWalletDTOById(Integer walletId) {
        return modelMapper.map(getWalletById(walletId), WalletDTO.class);
    }

    public Wallet getWalletById(Integer walletId) {
        return walletRepository.findById(walletId).orElse(null);
    }

    public WalletDTO saveWallet(WalletDTO dto) {
        return modelMapper.map(walletRepository.save(modelMapper.map(dto, Wallet.class)), WalletDTO.class);
    }

    public void deleteWallet(Integer walletId) {
        walletRepository.deleteById(walletId);
    }

    public List<WalletDTO> getWalletsInHousehold(Integer householdId) throws Exception {
        List<User> usersInHousehold = userRepository.findAllByHousehold_Id(householdId).orElse(Collections.emptyList());

        if (usersInHousehold.isEmpty()) {
            throw new Exception("Household not found!");
        }

        List<WalletDTO> walletsInHousehold = new ArrayList<>();

        usersInHousehold.forEach(user -> {
            Wallet walletTmp = walletRepository.findByUserId(user.getId()).orElse(null);
            if (walletTmp != null) {
                walletsInHousehold.add(modelMapper.map(walletTmp, WalletDTO.class));
            }
        });

        return walletsInHousehold;
    }

    public void updateWallet(Integer userId, WalletDTO walletDTO) throws Exception {
        Wallet walletTmp = walletRepository.findById(walletDTO.getId()).orElse(null);

        if (!walletTmp.getUser().getId().equals(userId)) {
            throw new Exception("Wallet doesnt belong to User!");
        }

        Wallet wallet = modelMapper.map(walletDTO, Wallet.class);
        wallet.setUser(walletTmp.getUser());

        walletRepository.save(wallet);
    }
}
