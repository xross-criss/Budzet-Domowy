package pl.dev.household.budget.manager.conf;

import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import pl.dev.household.budget.manager.security.xauth.TokenProvider;
import pl.dev.household.budget.manager.security.xauth.XAuthTokenFilter;

public class XAuthTokenConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private TokenProvider tokenProvider;

    private UserDetailsService detailsService;

    public XAuthTokenConfigurer(UserDetailsService detailsService, TokenProvider tokenProvider) {
        this.detailsService = detailsService;
        this.tokenProvider = tokenProvider;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        XAuthTokenFilter customFilter = new XAuthTokenFilter(detailsService, tokenProvider);
        http.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);
    }

}
