package pl.dev.household.budget.manager.conf.cron;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.dev.household.budget.manager.dictionaries.BalanceType;
import pl.dev.household.budget.manager.services.BalanceService;

@Component
public class BalanceReportCronJobs {

    BalanceService balanceService;

    public BalanceReportCronJobs(BalanceService balanceService) {
        this.balanceService = balanceService;
    }

    @Scheduled(cron = "00 01 00 * * *")
    public void generateSummaryBalanceReportCronJob() throws Exception {
        balanceService.generateAndSaveCronJob(BalanceType.SUMMARY);
    }

    @Scheduled(cron = "00 10 00 01 * *")
    public void generatePredictionBalanceReportCronJob() throws Exception {
        balanceService.generateAndSaveCronJob(BalanceType.PREDICTION);
    }

    @Scheduled(cron = "05 00 00 15 * *")
    public void generateBalanceReportCronJob() throws Exception {
        balanceService.generateAndSaveCronJob(BalanceType.GENERATED);
    }

}
