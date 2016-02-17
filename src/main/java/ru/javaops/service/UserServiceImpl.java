package ru.javaops.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.javaops.LoggedUser;
import ru.javaops.model.User;
import ru.javaops.repository.UserRepository;

import java.util.Collection;

/**
 * Authenticate a user from the database.
 */
@Service("userDetailsService")
public class UserServiceImpl implements UserService, org.springframework.security.core.userdetails.UserDetailsService {

    private final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Override
    public LoggedUser loadUserByUsername(final String email) {
        String lowercaseLogin = email.toLowerCase();
        log.debug("Authenticating {}", email);
        User user = userRepository.findByEmail(lowercaseLogin);
        if (user == null) {
            throw new UsernameNotFoundException("User " + lowercaseLogin + " was not found in the database");
        }
        if (!user.isActive()) {
            throw new DisabledException("User " + lowercaseLogin + " was not activated");
        }
        return new LoggedUser(user);
    }


    @Transactional
    public void deleteByEmail(String email) {
        log.debug("Delete user " + email);
        User user = userRepository.findByEmail(email);
        if (user != null) {
            userRepository.delete(user);
        } else {
            log.warn("User " + email + " is not found");
        }
    }

    public Collection<User> getGroup(String groupName) {
        return userRepository.findByGroupName(groupName);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void save(User u) {
        userRepository.save(u);
    }
}
