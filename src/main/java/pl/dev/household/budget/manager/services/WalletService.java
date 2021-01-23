package pl.dev.household.budget.manager.services;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;
import pl.dev.household.budget.manager.dao.User;
import pl.dev.household.budget.manager.dao.Wallet;
import pl.dev.household.budget.manager.dao.repository.UserRepository;
import pl.dev.household.budget.manager.dao.repository.WalletRepository;
import pl.dev.household.budget.manager.domain.ReportIntDTO;
import pl.dev.household.budget.manager.domain.WalletDTO;

import java.math.BigDecimal;
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
        List<User> userInHousehold = userRepository.findAllByHousehold_Id(householdId).orElse(Collections.emptyList());

        List<Wallet> walletsInHousehold = new ArrayList<>();

        userInHousehold.forEach(user ->
                walletsInHousehold.addAll(
                        walletRepository.findAllByUserId(user.getId())
                                .orElse(Collections.emptyList()))
        );

        return modelMapper.map(walletsInHousehold, new TypeToken<List<WalletDTO>>() {
        }.getType());
    }

    public void updateWallet(User user, WalletDTO walletDTO) throws Exception {
        Wallet wallet = modelMapper.map(walletDTO, Wallet.class);

        if (wallet.getUser() == null || wallet.getId() == null) {
            wallet.setUser(user);
        }

        walletRepository.save(wallet);
    }

    public ReportIntDTO countWalletBalance(Integer householdId) throws Exception {
        List<WalletDTO> walletsInHousehold = getWalletsInHousehold(householdId);
        ReportIntDTO report = new ReportIntDTO();
        BigDecimal amount = BigDecimal.valueOf(0);

        if (!walletsInHousehold.isEmpty()) {
            for (WalletDTO walletDTO : walletsInHousehold) {
                amount = amount.add(walletDTO.getAmount());
            }
        }

        report.setAmount(amount);

        return report;
    }
}
