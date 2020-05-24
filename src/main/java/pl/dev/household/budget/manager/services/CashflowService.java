package pl.dev.household.budget.manager.services;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.dev.household.budget.manager.dao.Cashflow;
import pl.dev.household.budget.manager.dao.repository.CashflowRepository;
import pl.dev.household.budget.manager.domain.CashflowDTO;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CashflowService {

    private ModelMapper modelMapper;
    private CashflowRepository cashflowRepository;

    @Autowired
    public CashflowService(CashflowRepository cashflowRepository) {
        this.cashflowRepository = cashflowRepository;
    }

    public List<CashflowDTO> getCashflows(Integer householdId) {
        return cashflowRepository.findAllByHousehold_Id(householdId).stream()
                .map(cashflow ->
                        modelMapper.map(cashflow, CashflowDTO.class)
                ).collect(Collectors.toList());
    }

    public CashflowDTO getCashflow(Integer cashflowId) {
        return modelMapper.map(cashflowRepository.findById(cashflowId), CashflowDTO.class);
    }

    public CashflowDTO addCashflow(CashflowDTO cashflowDTO) {
        Integer cashflowId = cashflowRepository.save(modelMapper.map(cashflowDTO, Cashflow.class)).getId();
        return getCashflow(cashflowId);
    }


    public CashflowDTO updateCashflow(Integer householdId, CashflowDTO cashflowDTO) {
        Optional<Cashflow> oldCashflow = cashflowRepository.findById(cashflowDTO.getId());
        if (oldCashflow.isEmpty() || !oldCashflow.get().getId().equals(cashflowDTO.getId())) {
            throw new RuntimeException("Household cannot be updated!");
        }

        Cashflow updatedCashflow = modelMapper.map(cashflowDTO, Cashflow.class);
        cashflowRepository.save(updatedCashflow);

        return getCashflow(updatedCashflow.getId());
    }
}
