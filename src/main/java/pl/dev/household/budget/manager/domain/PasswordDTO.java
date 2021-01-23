package pl.dev.household.budget.manager.domain;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PasswordDTO {
    String newPassword;
    String oldPassword;
}
