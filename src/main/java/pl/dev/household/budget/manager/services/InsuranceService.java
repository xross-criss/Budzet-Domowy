package pl.dev.household.budget.manager.services;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.dev.household.budget.manager.dao.InsuranceDAO;
import pl.dev.household.budget.manager.dao.repository.InsuranceRepository;
import pl.dev.household.budget.manager.domain.Insurance;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class InsuranceService {

    private ModelMapper modelMapper;
    private InsuranceRepository insuranceRepository;

    @Autowired
    public InsuranceService(InsuranceRepository insuranceRepository) {
        this.insuranceRepository = insuranceRepository;
    }

    public List<Insurance> getInsurances(Integer householdId) {
        return insuranceRepository.findAllByHousehold_Id(householdId).stream()
                .map(insurance -> modelMapper.map(insurance, Insurance.class))
                .collect(Collectors.toList());
    }

    public Insurance getInsurance(Integer insuranceId) {
        return modelMapper.map(insuranceRepository.findById(insuranceId), Insurance.class);
    }

    public Insurance addInsurance(Insurance insurance) {
        Integer insuranceId = insuranceRepository.save(modelMapper.map(insurance, InsuranceDAO.class)).getId();
        return getInsurance(insuranceId);
    }

    public Insurance updateInsurance(Integer householdId, Insurance insurance) {
        Optional<InsuranceDAO> oldInsurance = insuranceRepository.findById(insurance.getId());
        if (oldInsurance.isEmpty() || !oldInsurance.get().getId().equals(insurance.getId())) {
            throw new RuntimeException("Insurance cannot be updated!");
        }

        InsuranceDAO updatedInsurance = modelMapper.map(insurance, InsuranceDAO.class);
        insuranceRepository.save(updatedInsurance);

        return getInsurance(updatedInsurance.getId());
    }
}
