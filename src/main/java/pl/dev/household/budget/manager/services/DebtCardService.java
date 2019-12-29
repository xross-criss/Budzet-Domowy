package pl.dev.household.budget.manager.services;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.dev.household.budget.manager.dao.DebtCardDAO;
import pl.dev.household.budget.manager.dao.repository.DebtCardRepository;
import pl.dev.household.budget.manager.domain.DebtCard;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class DebtCardService {

    private ModelMapper modelMapper;
    private DebtCardRepository debtCardRepository;

    @Autowired
    public DebtCardService(DebtCardRepository debtCardRepository) {
        this.debtCardRepository = debtCardRepository;
    }

    public List<DebtCard> getDebtCards(Integer householdId) {
        return debtCardRepository.findAllByHousehold_Id(householdId).stream()
                .map(debtCard ->
                        modelMapper.map(debtCard, DebtCard.class)
                ).collect(Collectors.toList());
    }

    public DebtCard getDebtCard(Integer debtCardId) {
        return modelMapper.map(debtCardRepository.findById(debtCardId), DebtCard.class);
    }


    public DebtCard addDebtCard(DebtCard debtCard) {
        Integer debtCardId = debtCardRepository.save(modelMapper.map(debtCard, DebtCardDAO.class)).getId();
        return getDebtCard(debtCardId);
    }


    public DebtCard updateDebtCard(Integer householdId, DebtCard debtCard) {
        Optional<DebtCardDAO> oldDebtCard = debtCardRepository.findById(debtCard.getId());
        if (oldDebtCard.isEmpty() || !oldDebtCard.get().getId().equals(debtCard.getId())) {
            throw new RuntimeException("DebtCard cannot be updated!");
        }

        DebtCardDAO updatedDebtCard = modelMapper.map(debtCard, DebtCardDAO.class);
        debtCardRepository.save(updatedDebtCard);

        return getDebtCard(updatedDebtCard.getId());
    }

}
