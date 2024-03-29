package pl.dev.household.budget.manager.conf;

import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import pl.dev.household.budget.manager.security.xauth.TokenProvider;

/**
 * Configures x-auth-token security.
 */
@Configuration
public class XAuthConfiguration implements EnvironmentAware {

    private Environment env;

    @Override
    public void setEnvironment(Environment environment) {
        this.env = environment;
    }

    @Bean
    public TokenProvider tokenProvider() {
        String secret = env.getProperty("secret", String.class, "mySecretXAuthSecret");
        int validityInSeconds = env.getProperty("tokenValidityInSeconds", Integer.class, 3600);
        return new TokenProvider(secret, validityInSeconds);
    }

}
