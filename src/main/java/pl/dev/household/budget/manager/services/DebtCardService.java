package pl.dev.household.budget.manager.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.dev.household.budget.manager.dao.DebtCardDAO;
import pl.dev.household.budget.manager.dao.repository.DebtCardRepository;
import pl.dev.household.budget.manager.domain.DebtCard;
import pl.dev.household.budget.manager.utils.DebtCardUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class DebtCardService {

    DebtCardRepository debtCardRepository;

    @Autowired
    public DebtCardService(DebtCardRepository debtCardRepository) {
        this.debtCardRepository = debtCardRepository;
    }

    public List<DebtCard> getDebtCards(Integer householdId) {
        return debtCardRepository.findAllByHousehold_Id(householdId).stream()
                .map(debtCard ->
                        DebtCardUtils.convertToDTO(
                                java.util.Optional.ofNullable(debtCard)
                        )
                ).collect(Collectors.toList());
    }

    public DebtCard getDebtCard(Integer debtCardId) {
        return DebtCardUtils.convertToDTO(debtCardRepository.findById(debtCardId));
    }


    public DebtCard addDebtCard(DebtCard debtCard) {
        Integer debtCardId = debtCardRepository.save(DebtCardUtils.convertToDAO(debtCard)).getId();
        return getDebtCard(debtCardId);
    }


    public DebtCard updateDebtCard(Integer householdId, DebtCard debtCard) {
        Optional<DebtCardDAO> oldDebtCard = debtCardRepository.findById(debtCard.getId());
        if (oldDebtCard.isEmpty() || !oldDebtCard.get().getId().equals(debtCard.getId())) {
            throw new RuntimeException("DebtCard cannot be updated!");
        }

        DebtCardDAO updatedDebtCard = DebtCardUtils.convertToDAO(debtCard);
        debtCardRepository.save(updatedDebtCard);

        return getDebtCard(updatedDebtCard.getId());
    }

}
