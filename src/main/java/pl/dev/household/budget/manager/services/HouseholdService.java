package pl.dev.household.budget.manager.services;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.dev.household.budget.manager.dao.HouseholdDAO;
import pl.dev.household.budget.manager.dao.repository.HouseholdRepository;
import pl.dev.household.budget.manager.domain.Household;

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

    public Household getHousehold(Integer householdId) {
        return modelMapper.map(householdRepository.findById(householdId), Household.class);
    }

    public Household updateHousehold(Integer householdId, Household household) {

        Optional<HouseholdDAO> oldHousehold = householdRepository.findById(householdId);
        if (oldHousehold.isEmpty() || !oldHousehold.get().getId().equals(household.getId())) {
            throw new RuntimeException("Household cannot be updated!");
        }

        HouseholdDAO updatedHousehold = modelMapper.map(household, HouseholdDAO.class);
        householdRepository.save(updatedHousehold);

        return getHousehold(householdId);
    }

    public Household addHousehold(Household household) {
        HouseholdDAO newHousehold = modelMapper.map(household, HouseholdDAO.class);
        householdRepository.save(newHousehold);

        return getHousehold(newHousehold.getId());
    }
}
