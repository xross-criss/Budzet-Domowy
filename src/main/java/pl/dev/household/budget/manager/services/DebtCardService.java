package pl.dev.household.budget.manager.services;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;
import pl.dev.household.budget.manager.dao.DebtCard;
import pl.dev.household.budget.manager.dao.User;
import pl.dev.household.budget.manager.dao.repository.DebtCardRepository;
import pl.dev.household.budget.manager.domain.DebtCardDTO;
import pl.dev.household.budget.manager.domain.ReportIntDTO;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class DebtCardService {

    private ModelMapper modelMapper;
    private DebtCardRepository debtCardRepository;
    private UserService userService;

    public DebtCardService(ModelMapper modelMapper, DebtCardRepository debtCardRepository, UserService userService) {
        this.modelMapper = modelMapper;
        this.debtCardRepository = debtCardRepository;
        this.userService = userService;
    }

    public List<DebtCardDTO> getDebtCards(Integer householdId) throws Exception {
        return getDebtCardsForHouseholdUsers(householdId);
    }

    public DebtCardDTO getDebtCard(Integer debtCardId) {
        return modelMapper.map(debtCardRepository.findById(debtCardId), DebtCardDTO.class);
    }


    public DebtCardDTO addDebtCard(DebtCardDTO debtCardDTO) {
        Integer debtCardId = debtCardRepository.save(modelMapper.map(debtCardDTO, DebtCard.class)).getId();
        return getDebtCard(debtCardId);
    }


    public DebtCardDTO updateDebtCard(Integer userId, DebtCardDTO debtCardDTO) {
       DebtCard oldDebtCard = debtCardRepository.findById(debtCardDTO.getId()).orElse(null);

        if (oldDebtCard == null) {
            return addDebtCard(debtCardDTO);
        }

        DebtCard updatedDebtCard = modelMapper.map(debtCardDTO, DebtCard.class);
        debtCardRepository.save(updatedDebtCard);

        return getDebtCard(updatedDebtCard.getId());
    }

    public ReportIntDTO countDebtCardBalance(Integer householdId) throws Exception {
        ReportIntDTO report = new ReportIntDTO();
        BigDecimal burdenTmp = BigDecimal.valueOf(0);

        List<DebtCardDTO> debtCardsList = getDebtCardsForHouseholdUsers(householdId);

        if (debtCardsList != null && !debtCardsList.isEmpty()) {
            for (DebtCardDTO debtCard : debtCardsList) {
                // obliczanie comiesięcznego zobowiązania z różnicy balansu konta karty debetowej
                if (!debtCard.getBalance().equals(debtCard.getLimit())) {
                    BigDecimal annualPercentageC = debtCard.getAnnualPercentage().divide(BigDecimal.valueOf(100), 4, RoundingMode.HALF_UP);
                    BigDecimal amount = ((debtCard.getLimit().subtract(debtCard.getBalance()))
                            .multiply(annualPercentageC))
                            .divide(BigDecimal.valueOf(12), 2, RoundingMode.HALF_UP);
                    burdenTmp = burdenTmp.add(amount);
                }

                //obliczanie odnowienia limitu
                if (LocalDate.now().getMonth().getValue() == 12) {
                    BigDecimal renewalPercentageC = debtCard.getRenewalPercentage().divide(BigDecimal.valueOf(100), 4, RoundingMode.HALF_UP);

                    BigDecimal amount = debtCard.getLimit()
                            .multiply(renewalPercentageC)
                            .setScale(2, RoundingMode.HALF_UP);
                    burdenTmp = burdenTmp.add(amount);
                }
            }
        }

        report.setBurden(burdenTmp);

        return report;
    }

    public List<DebtCard> aggregateDebtCards(Integer userId) {
        List<DebtCard> optList = debtCardRepository.findAllByUserId(userId).orElse(Collections.emptyList());

        return optList;
    }

    public void deleteDebtCard(Integer userId, Integer debtCardId) {
        debtCardRepository.deleteById(debtCardId);
    }

    private List<DebtCardDTO> getDebtCardsForHouseholdUsers(Integer householdId) throws Exception {
        List<User> usersInHousehold = userService.getAllUsersInHouseholdByHousehold(householdId);

        if (usersInHousehold.isEmpty()) {
            throw new Exception("No users in household found!");
        }

        List<DebtCard> debtCards = new ArrayList<>();

        usersInHousehold.forEach(user -> {
            debtCards.addAll(debtCardRepository.findAllByUserId(user.getId()).orElse(Collections.emptyList()));
        });

        return modelMapper.map(debtCards, new TypeToken<List<DebtCardDTO>>() {
        }.getType());
    }
}
