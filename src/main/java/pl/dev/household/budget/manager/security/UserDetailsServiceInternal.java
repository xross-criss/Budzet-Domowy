package pl.dev.household.budget.manager.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.dev.household.budget.manager.dao.User;
import pl.dev.household.budget.manager.dao.repository.UserRepository;

@Component("userDetailsServiceInternal")
public class UserDetailsServiceInternal {

    private final Logger log = LoggerFactory.getLogger(UserDetailsServiceInternal.class);

    private final UserRepository userRepository;

    @Autowired
    public UserDetailsServiceInternal(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public UserDetails loadUserByUsername(final String login) {
        User userFromDatabase = userRepository.findOneByLogin(login);
        checkUser(userFromDatabase, login);
        return new UserDetailsWrapper(userFromDatabase);
    }

    private void checkUser(User userFromDatabase, String login) {
        if (userFromDatabase == null) {
            throw new UsernameNotFoundException("User " + login + " was not found in the database");
        }
    }

}
