package pl.dev.household.budget.manager.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.MessageDigestPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.dev.household.budget.manager.security.Http401UnauthorizedEntryPoint;
import pl.dev.household.budget.manager.security.xauth.TokenProvider;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, jsr250Enabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @SuppressWarnings("SpringAutowiredFieldsWarningInspection")
    @Autowired
    private Http401UnauthorizedEntryPoint authenticationEntryPoint;

    @SuppressWarnings("SpringAutowiredFieldsWarningInspection")
    @Autowired
    private UserDetailsService userDetailsService;

    @SuppressWarnings("SpringAutowiredFieldsWarningInspection")
    @Autowired
    private TokenProvider tokenProvider;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new MessageDigestPasswordEncoder("MD5");
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/scripts/**/*.{js,html}")
                .antMatchers("/bower_components/**")
                .antMatchers("/i18n/**")
                .antMatchers("/assets/**")
                .antMatchers("/swagger-ui/**")
                .antMatchers("/test/**")
                .antMatchers("/console/**");
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint)
                .and()
                .csrf()
                .disable()
                .headers()
                .frameOptions()
                .disable()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/api/register").permitAll()
                .antMatchers("/api/authenticate").permitAll()
                .antMatchers("/api/authenticate/**").permitAll()
                .antMatchers("/api/home").permitAll()
                .antMatchers("/api/**").authenticated()
                .antMatchers("/protected/**").authenticated()
                .and()
                .apply(securityConfigurerAdapter());
    }

    private SecurityConfigurerAdapter securityConfigurerAdapter() {
        return new XAuthTokenConfigurer(userDetailsService, tokenProvider);
    }

}