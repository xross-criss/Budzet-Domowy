package pl.dev.household.budget.manager.services;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.dev.household.budget.manager.dao.Investment;
import pl.dev.household.budget.manager.dao.repository.InvestmentRepository;
import pl.dev.household.budget.manager.domain.InvestmentDTO;

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

    public List<InvestmentDTO> getInvestments(Integer householdId) {
        return investmentRepository.findAllByHousehold_Id(householdId).stream()
                .map(investment -> modelMapper.map(investment, InvestmentDTO.class))
                .collect(Collectors.toList());
    }

    public InvestmentDTO getInvestment(Integer investmentId) {
        return modelMapper.map(investmentRepository.findById(investmentId), InvestmentDTO.class);
    }

    public InvestmentDTO addInvestment(InvestmentDTO investmentDTO) {
        Integer investmentId = investmentRepository.save(modelMapper.map(investmentDTO, Investment.class)).getId();
        return getInvestment(investmentId);
    }

    public InvestmentDTO updateInvestment(Integer householdId, InvestmentDTO investmentDTO) {
        Optional<Investment> oldInvestment = investmentRepository.findById(investmentDTO.getId());
        if (oldInvestment.isEmpty() || !oldInvestment.get().getId().equals(investmentDTO.getId())) {
            throw new RuntimeException("Investment cannot be updated!");
        }

        Investment updatedInvestment = modelMapper.map(investmentDTO, Investment.class);
        investmentRepository.save(updatedInvestment);

        return getInvestment(updatedInvestment.getId());
    }
}
