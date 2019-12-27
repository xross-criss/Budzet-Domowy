package pl.dev.household.budget.manager.utils;


import pl.dev.household.budget.manager.dao.InvestmentDAO;
import pl.dev.household.budget.manager.domain.Investment;

import java.util.Optional;

public class InvestmentUtils {

    public static Investment convertToDTO(Optional<InvestmentDAO> investmentDAO) {
        Investment investment = new Investment();

        if (investmentDAO.isEmpty()) {
            throw new RuntimeException("No cashflow");
        }

        investment.setId(investmentDAO.get().getId());
        investment.setHousehold(HouseholdUtils.convertToDTO(Optional.ofNullable(investmentDAO.get().getHousehold())));
        investment.setType(investmentDAO.get().getType());
        investment.setIsMonthly(investmentDAO.get().getIsMonthly());
        investment.setPeriod(investmentDAO.get().getPeriod());
        investment.setEndDate(investmentDAO.get().getEndDate());
        investment.setInvestmentPercentage(investmentDAO.get().getInvestmentPercentage());
        investment.setAmount(investmentDAO.get().getAmount());
        investment.setName(investmentDAO.get().getName());

        return investment;
    }

    public static InvestmentDAO convertToDAO(Investment investment) {
        InvestmentDAO investmentDAO = new InvestmentDAO();

        investmentDAO.setId(investment.getId());
        investmentDAO.setHousehold(HouseholdUtils.convertToDAO(investment.getHousehold()));
        investmentDAO.setType(investment.getType());
        investmentDAO.setIsMonthly(investment.getIsMonthly());
        investmentDAO.setPeriod(investment.getPeriod());
        investmentDAO.setEndDate(investment.getEndDate());
        investmentDAO.setInvestmentPercentage(investment.getInvestmentPercentage());
        investmentDAO.setAmount(investment.getAmount());
        investmentDAO.setName(investment.getName());

        return investmentDAO;
    }

}
