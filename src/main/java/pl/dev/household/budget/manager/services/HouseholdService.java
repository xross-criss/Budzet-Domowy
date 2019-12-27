package pl.dev.household.budget.manager.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.dev.household.budget.manager.dao.HouseholdDAO;
import pl.dev.household.budget.manager.dao.repository.HouseholdRepository;
import pl.dev.household.budget.manager.domain.Household;
import pl.dev.household.budget.manager.utils.HouseholdUtils;

import java.util.Optional;

@Slf4j
@Service
public class HouseholdService {

    private ObjectMapper objectMapper;
    private final HouseholdRepository householdRepository;

    @Autowired
    public HouseholdService(ObjectMapper objectMapper, HouseholdRepository householdRepository) {
        this.objectMapper = objectMapper;
        this.householdRepository = householdRepository;
    }

    public Household getHousehold(Integer householdId) {
        return HouseholdUtils.convertToDTO(householdRepository.findById(householdId));
    }

    public Household updateHousehold(Integer householdId, Household household) {

        Optional<HouseholdDAO> oldHousehold = householdRepository.findById(householdId);
        if (oldHousehold.isEmpty() || !oldHousehold.get().getId().equals(household.getId())) {
            throw new RuntimeException("Household cannot be updated!");
        }

        HouseholdDAO updatedHousehold = HouseholdUtils.convertToDAO(household);
        householdRepository.save(updatedHousehold);

        return getHousehold(householdId);
    }

    public Household addHousehold(Household household) {
        HouseholdDAO newHousehold = HouseholdUtils.convertToDAO(household);
        householdRepository.save(newHousehold);

        return getHousehold(newHousehold.getId());
    }
}
