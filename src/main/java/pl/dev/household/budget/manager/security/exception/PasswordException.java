package pl.dev.household.budget.manager.security.exception;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FORBIDDEN)
public class PasswordException extends AuthenticationException {

    public PasswordException(String message) {
        super(message);
    }

    public PasswordException(String message, Throwable t) {
        super(message, t);
    }

}