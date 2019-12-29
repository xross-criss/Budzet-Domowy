package pl.dev.household.budget.manager.services;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.dev.household.budget.manager.dao.CashflowDAO;
import pl.dev.household.budget.manager.dao.repository.CashflowRepository;
import pl.dev.household.budget.manager.domain.Cashflow;

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

    public List<Cashflow> getCashflows(Integer householdId) {
        return cashflowRepository.findAllByHousehold_Id(householdId).stream()
                .map(cashflow ->
                        modelMapper.map(cashflow, Cashflow.class)
                ).collect(Collectors.toList());
    }

    public Cashflow getCashflow(Integer cashflowId) {
        return modelMapper.map(cashflowRepository.findById(cashflowId), Cashflow.class);
    }

    public Cashflow addCashflow(Cashflow cashflow) {
        Integer cashflowId = cashflowRepository.save(modelMapper.map(cashflow, CashflowDAO.class)).getId();
        return getCashflow(cashflowId);
    }


    public Cashflow updateCashflow(Integer householdId, Cashflow cashflow) {
        Optional<CashflowDAO> oldCashflow = cashflowRepository.findById(cashflow.getId());
        if (oldCashflow.isEmpty() || !oldCashflow.get().getId().equals(cashflow.getId())) {
            throw new RuntimeException("Household cannot be updated!");
        }

        CashflowDAO updatedCashflow = modelMapper.map(cashflow, CashflowDAO.class);
        cashflowRepository.save(updatedCashflow);

        return getCashflow(updatedCashflow.getId());
    }
}
