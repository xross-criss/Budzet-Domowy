package pl.dev.household.budget.manager.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.dev.household.budget.manager.domain.AuthenticateRequestDTO;
import pl.dev.household.budget.manager.domain.TokenDTO;
import pl.dev.household.budget.manager.security.UserDetailsServiceInternal;
import pl.dev.household.budget.manager.security.UserDetailsWrapper;
import pl.dev.household.budget.manager.security.xauth.TokenProvider;


@Slf4j
@RestController
@RequestMapping("/api/authenticate")
public class AuthenticatonController {

    private TokenProvider tokenProvider;
    private AuthenticationManager authenticationManager;
    private UserDetailsServiceInternal userDetailsService;

    public AuthenticatonController(TokenProvider tokenProvider, AuthenticationManager authenticationManager, UserDetailsServiceInternal userDetailsService) {
        this.tokenProvider = tokenProvider;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
    }

    @PostMapping
    public ResponseEntity<TokenDTO> authorize(@RequestBody AuthenticateRequestDTO credentials) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(credentials.getLogin(), credentials.getPassword());
        Authentication authentication = this.authenticationManager.authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsWrapper details = (UserDetailsWrapper) this.userDetailsService.loadUserByUsername(credentials.getLogin());
        return ResponseEntity.ok(new TokenDTO(tokenProvider.createToken(details)));
    }

}
