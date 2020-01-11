package pl.dev.household.budget.manager.security.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import pl.dev.household.budget.manager.dao.UserDAO;
import pl.dev.household.budget.manager.security.UserDetailsWrapper;

import java.util.Optional;

/**
 * Provides security utils.
 */
public class Security {

    private Security() {

    }

    public static UserDAO currentUser() {
        return Optional.ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .map(Authentication::getPrincipal)
                .map(UserDetailsWrapper.class::cast)
                .map(UserDetailsWrapper::getUser)
                .orElse(null);
    }

}
