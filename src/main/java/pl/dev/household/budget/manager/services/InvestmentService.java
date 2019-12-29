package pl.dev.household.budget.manager.services;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.dev.household.budget.manager.dao.InvestmentDAO;
import pl.dev.household.budget.manager.dao.repository.InvestmentRepository;
import pl.dev.household.budget.manager.domain.Investment;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class InvestmentService {

    private ModelMapper modelMapper;
    private InvestmentRepository investmentRepository;

    @Autowired
    public InvestmentService(InvestmentRepository investmentRepository) {
        this.investmentRepository = investmentRepository;
    }

    public List<Investment> getInvestments(Integer householdId) {
        return investmentRepository.findAllByHousehold_Id(householdId).stream()
                .map(investment -> modelMapper.map(investment, Investment.class))
                .collect(Collectors.toList());
    }

    public Investment getInvestment(Integer investmentId) {
        return modelMapper.map(investmentRepository.findById(investmentId), Investment.class);
    }

    public Investment addInvestment(Investment investment) {
        Integer investmentId = investmentRepository.save(modelMapper.map(investment, InvestmentDAO.class)).getId();
        return getInvestment(investmentId);
    }

    public Investment updateInvestment(Integer householdId, Investment investment) {
        Optional<InvestmentDAO> oldInvestment = investmentRepository.findById(investment.getId());
        if (oldInvestment.isEmpty() || !oldInvestment.get().getId().equals(investment.getId())) {
            throw new RuntimeException("Investment cannot be updated!");
        }

        InvestmentDAO updatedInvestment = modelMapper.map(investment, InvestmentDAO.class);
        investmentRepository.save(updatedInvestment);

        return getInvestment(updatedInvestment.getId());
    }
}
