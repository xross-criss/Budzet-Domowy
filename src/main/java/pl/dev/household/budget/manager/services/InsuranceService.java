package pl.dev.household.budget.manager.services;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;
import pl.dev.household.budget.manager.dao.Insurance;
import pl.dev.household.budget.manager.dao.User;
import pl.dev.household.budget.manager.dao.repository.InsuranceRepository;
import pl.dev.household.budget.manager.dictionaries.InsuranceType;
import pl.dev.household.budget.manager.domain.InsuranceDTO;
import pl.dev.household.budget.manager.domain.ReportIntDTO;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Period;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Slf4j
@Service
public class InsuranceService {

    private ModelMapper modelMapper;
    private InsuranceRepository insuranceRepository;
    private UserService userService;

    public InsuranceService(ModelMapper modelMapper, InsuranceRepository insuranceRepository, UserService userService) {
        this.modelMapper = modelMapper;
        this.insuranceRepository = insuranceRepository;
        this.userService = userService;
    }

    public List<InsuranceDTO> getInsurances(Integer householdId) throws Exception {
        return getInsurancesForHouseholdUsers(householdId);
    }

    public InsuranceDTO getInsurance(Integer insuranceId) {
        return modelMapper.map(insuranceRepository.findById(insuranceId), InsuranceDTO.class);
    }

    public InsuranceDTO addInsurance(InsuranceDTO insuranceDTO) {
        if (!insuranceDTO.getType().equals(InsuranceType.CAR)) {
            insuranceDTO.setVehicle(null);
        }

        Integer insuranceId = insuranceRepository.save(modelMapper.map(insuranceDTO, Insurance.class)).getId();
        return getInsurance(insuranceId);
    }

    public InsuranceDTO updateInsurance(Integer userId, InsuranceDTO insuranceDTO) {
        Optional<Insurance> oldInsurance = insuranceRepository.findById(insuranceDTO.getId());
        if (oldInsurance.isEmpty() || !oldInsurance.get().getId().equals(insuranceDTO.getId())) {
            throw new RuntimeException("Insurance cannot be updated!");
        }

        Insurance updatedInsurance = modelMapper.map(insuranceDTO, Insurance.class);
        insuranceRepository.save(updatedInsurance);

        return getInsurance(updatedInsurance.getId());
    }

    public ReportIntDTO countInsuranceBalance(Integer householdId) throws Exception {
        ReportIntDTO report = new ReportIntDTO();
        BigDecimal burdenTmp = BigDecimal.valueOf(0);

        List<InsuranceDTO> insurancesList = aggregateInsurances(householdId);

        if (insurancesList != null && !insurancesList.isEmpty()) {
            for (InsuranceDTO insurance : insurancesList) {
                burdenTmp = burdenTmp.add(
                        insurance.getCost()
                                .divide(BigDecimal.valueOf(insurance.getPeriod()), 2, RoundingMode.HALF_UP));
            }
        }

        report.setBurden(burdenTmp);
        return report;
    }

    public List<InsuranceDTO> aggregateInsurancesForCurrentMonth(Integer householdId) throws Exception {
        List<InsuranceDTO> tmp = aggregateInsurances(householdId).stream().map(insurance -> modelMapper.map(insurance, InsuranceDTO.class)).collect(Collectors.toList());
        return tmp;
    }

    private List<InsuranceDTO> getInsurancesForHouseholdUsers(Integer householdId) throws Exception {
        List<User> usersInHousehold = userService.getAllUsersInHouseholdByHousehold(householdId);

        if (usersInHousehold.isEmpty()) {
            throw new Exception("No users in household found!");
        }

        List<Insurance> insurancesList = new ArrayList<>();

        usersInHousehold.forEach(user -> {
            insurancesList.addAll(insuranceRepository.findAllByUserId(user.getId()).orElse(Collections.emptyList()));
        });

        return modelMapper.map(insurancesList, new TypeToken<List<InsuranceDTO>>() {
        }.getType());
    }

    private List<InsuranceDTO> aggregateInsurances(Integer householdId) throws Exception {
        return getInsurancesForHouseholdUsers(householdId).stream()
                .filter(insurance -> YearMonth.now().atEndOfMonth().isBefore(insurance.getEndDate()))
                .filter(checkIfMonthIsPeriodicForInsurance())
                .collect(Collectors.toList());
    }

    private static Predicate<InsuranceDTO> checkIfMonthIsPeriodicForInsurance() {
        return p -> {
            Period period = Period.between(p.getEndDate().minusMonths(12), LocalDate.now());
            int periodInt;
            if (period.getMonths() == 0) {
                periodInt = 1;
            } else {
                periodInt = period.getMonths();
            }

            return periodInt % p.getPeriod() == 0;
        };
    }
}
