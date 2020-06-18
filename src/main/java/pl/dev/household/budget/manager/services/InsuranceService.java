package pl.dev.household.budget.manager.services;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import pl.dev.household.budget.manager.dao.Insurance;
import pl.dev.household.budget.manager.dao.repository.InsuranceRepository;
import pl.dev.household.budget.manager.domain.InsuranceDTO;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class InsuranceService {

    private ModelMapper modelMapper;
    private InsuranceRepository insuranceRepository;

    public InsuranceService(ModelMapper modelMapper, InsuranceRepository insuranceRepository) {
        this.modelMapper = modelMapper;
        this.insuranceRepository = insuranceRepository;
    }

    public List<InsuranceDTO> getInsurances(Integer householdId) {
        return insuranceRepository.findAllByHousehold_Id(householdId).stream()
                .map(insurance -> modelMapper.map(insurance, InsuranceDTO.class))
                .collect(Collectors.toList());
    }

    public InsuranceDTO getInsurance(Integer insuranceId) {
        return modelMapper.map(insuranceRepository.findById(insuranceId), InsuranceDTO.class);
    }

    public InsuranceDTO addInsurance(InsuranceDTO insuranceDTO) {
        Integer insuranceId = insuranceRepository.save(modelMapper.map(insuranceDTO, Insurance.class)).getId();
        return getInsurance(insuranceId);
    }

    public InsuranceDTO updateInsurance(Integer householdId, InsuranceDTO insuranceDTO) {
        Optional<Insurance> oldInsurance = insuranceRepository.findById(insuranceDTO.getId());
        if (oldInsurance.isEmpty() || !oldInsurance.get().getId().equals(insuranceDTO.getId())) {
            throw new RuntimeException("Insurance cannot be updated!");
        }

        Insurance updatedInsurance = modelMapper.map(insuranceDTO, Insurance.class);
        insuranceRepository.save(updatedInsurance);

        return getInsurance(updatedInsurance.getId());
    }
}
