package pl.dev.household.budget.manager.services;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import pl.dev.household.budget.manager.dao.Wallet;
import pl.dev.household.budget.manager.dao.repository.WalletRepository;
import pl.dev.household.budget.manager.domain.WalletDTO;

@Slf4j
@Service
public class WalletService {

    private ModelMapper modelMapper;
    private WalletRepository walletRepository;

    public WalletService(ModelMapper modelMapper, WalletRepository walletRepository) {
        this.modelMapper = modelMapper;
        this.walletRepository = walletRepository;
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

}
