package pl.dev.household.budget.manager.domain;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ReportIntDTO {

    BigDecimal income;
    BigDecimal burden;
    BigDecimal amount;

}
