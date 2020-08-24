package pl.dev.household.budget.manager.services;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import pl.dev.household.budget.manager.dao.Insurance;
import pl.dev.household.budget.manager.dao.repository.InsuranceRepository;
import pl.dev.household.budget.manager.domain.InsuranceDTO;
import pl.dev.household.budget.manager.domain.ReportIntDTO;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Period;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
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

    public ReportIntDTO countInsuranceBalance(Integer householdId) {
        ReportIntDTO report = new ReportIntDTO();
        BigDecimal burdenTmp = BigDecimal.valueOf(0);

        List<Insurance> insurancesList = aggregateInsurances(householdId);

        if (insurancesList != null && !insurancesList.isEmpty()) {
            for (Insurance insurance : insurancesList) {
                burdenTmp = burdenTmp.add(
                        insurance.getCost()
                                .divide(BigDecimal.valueOf(insurance.getPeriod()))
                                .setScale(2, RoundingMode.CEILING));
            }
        }

        report.setBurden(burdenTmp);
        return report;
    }

    public List<InsuranceDTO> aggregateInsurancesForCurrentMonth(Integer householdId) {
        return aggregateInsurances(householdId).stream().map(insurance -> modelMapper.map(insurance, InsuranceDTO.class)).collect(Collectors.toList());
    }

    private List<Insurance> aggregateInsurances(Integer householdId) {
        return insuranceRepository.findAllByHousehold_Id(householdId).stream()
                .filter(insurance -> insurance.getEndDate().isBefore(YearMonth.now().atEndOfMonth()))
                .filter(checkIfMonthIsPeriodicForInsurance())
                .collect(Collectors.toList());
    }

    private static Predicate<Insurance> checkIfMonthIsPeriodicForInsurance() {
        return p -> Period.between(p.getEndDate().minusMonths(12), LocalDate.now()).getMonths() % p.getPeriod() == 0;
    }
}
