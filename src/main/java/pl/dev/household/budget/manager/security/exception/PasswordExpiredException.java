package pl.dev.household.budget.manager.security.exception;

import org.springframework.security.core.AuthenticationException;

public class PasswordExpiredException extends AuthenticationException {

    public PasswordExpiredException(String message) {
        super(message);
    }

    public PasswordExpiredException(String message, Throwable t) {
        super(message, t);
    }

}