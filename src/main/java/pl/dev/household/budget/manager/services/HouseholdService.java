package pl.dev.household.budget.manager.services;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.dev.household.budget.manager.dao.Household;
import pl.dev.household.budget.manager.dao.repository.HouseholdRepository;
import pl.dev.household.budget.manager.domain.HouseholdDTO;

import java.util.Optional;

@Slf4j
@Service
public class HouseholdService {

    private ModelMapper modelMapper;
    private HouseholdRepository householdRepository;

    @Autowired
    public HouseholdService(HouseholdRepository householdRepository) {
        this.householdRepository = householdRepository;
    }

    public HouseholdDTO getHousehold(Integer householdId) {
        return modelMapper.map(householdRepository.findById(householdId), HouseholdDTO.class);
    }

    public HouseholdDTO updateHousehold(Integer householdId, HouseholdDTO householdDTO) {

        Optional<Household> oldHousehold = householdRepository.findById(householdId);
        if (oldHousehold.isEmpty() || !oldHousehold.get().getId().equals(householdDTO.getId())) {
            throw new RuntimeException("Household cannot be updated!");
        }

        Household updatedHousehold = modelMapper.map(householdDTO, Household.class);
        householdRepository.save(updatedHousehold);

        return getHousehold(householdId);
    }

    public HouseholdDTO addHousehold(HouseholdDTO householdDTO) {
        Household newHousehold = modelMapper.map(householdDTO, Household.class);
        householdRepository.save(newHousehold);

        return getHousehold(newHousehold.getId());
    }
}
