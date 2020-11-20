package pl.dev.household.budget.manager.services;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import pl.dev.household.budget.manager.dao.DebtCard;
import pl.dev.household.budget.manager.dao.repository.DebtCardRepository;
import pl.dev.household.budget.manager.domain.DebtCardDTO;
import pl.dev.household.budget.manager.domain.ReportIntDTO;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class DebtCardService {

    private ModelMapper modelMapper;
    private DebtCardRepository debtCardRepository;

    public DebtCardService(ModelMapper modelMapper, DebtCardRepository debtCardRepository) {
        this.modelMapper = modelMapper;
        this.debtCardRepository = debtCardRepository;
    }

    public List<DebtCardDTO> getDebtCards(Integer householdId) {
        Optional<List<DebtCard>> optList = Optional.of(debtCardRepository.findAllByHousehold_Id(householdId).orElse(Collections.emptyList()));

        return optList.stream()
                .flatMap(Collection::stream)
                .map(debtCard ->
                        modelMapper.map(debtCard, DebtCardDTO.class)
                ).collect(Collectors.toList());
    }

    public DebtCardDTO getDebtCard(Integer debtCardId) {
        return modelMapper.map(debtCardRepository.findById(debtCardId), DebtCardDTO.class);
    }


    public DebtCardDTO addDebtCard(DebtCardDTO debtCardDTO) {
        Integer debtCardId = debtCardRepository.save(modelMapper.map(debtCardDTO, DebtCard.class)).getId();
        return getDebtCard(debtCardId);
    }


    public DebtCardDTO updateDebtCard(Integer householdId, DebtCardDTO debtCardDTO) {
        Optional<DebtCard> oldDebtCard = Optional.of(debtCardRepository.findById(debtCardDTO.getId())).orElse(null);

        if (oldDebtCard.isEmpty()) {
            return addDebtCard(debtCardDTO);
        }

        DebtCard updatedDebtCard = modelMapper.map(debtCardDTO, DebtCard.class);
        debtCardRepository.save(updatedDebtCard);

        return getDebtCard(updatedDebtCard.getId());
    }

    public ReportIntDTO countDebtCardBalance(Integer householdId) {
        ReportIntDTO report = new ReportIntDTO();
        BigDecimal burdenTmp = BigDecimal.valueOf(0);

        List<DebtCard> debtCardsList = aggregateDebtCards(householdId);

        if (debtCardsList != null && !debtCardsList.isEmpty()) {
            for (DebtCard debtCard : debtCardsList) {
                // obliczanie comiesięcznego zobowiązania z różnicy balansu konta karty debetowej
                if (!debtCard.getBalance().equals(debtCard.getLimit())) {
                    BigDecimal amount = ((debtCard.getLimit().subtract(debtCard.getBalance()))
                            .multiply(debtCard.getAnnualPercentage()))
                            .divide(BigDecimal.valueOf(12), 2, RoundingMode.HALF_UP);
                    burdenTmp = burdenTmp.add(amount);
                }

                //obliczanie odnowienia limitu
                if (LocalDate.now().getMonth().getValue() == 12) {
                    BigDecimal amount = debtCard.getLimit()
                            .multiply(debtCard.getRenewalPercentage())
                            .setScale(2, RoundingMode.CEILING);
                    burdenTmp = burdenTmp.add(amount);
                }
            }
        }

        report.setBurden(burdenTmp);

        return report;
    }

    public List<DebtCard> aggregateDebtCards(Integer householdId) {
        Optional<List<DebtCard>> optList = Optional.of(debtCardRepository.findAllByHousehold_Id(householdId).orElse(Collections.emptyList()));

        return optList.get();
    }

    public void deleteDebtCard(Integer householdId, Integer debtCardId) {
        debtCardRepository.deleteById(debtCardId);
    }
}
