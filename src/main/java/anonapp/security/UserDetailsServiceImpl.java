package anonapp.security;

import anonapp.data.service.UserService;
import anonapp.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author Orlov Diga
 */
@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserService userService;

    @Autowired
    public UserDetailsServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findByUsername(username);

        if (user != null) {
            return user;
        }

        throw new UsernameNotFoundException("User " + username + " not found.");
    }

    private UserDetails toUserDetails(UserObject userObject) {
        return org.springframework.security.core.userdetails.User.withUsername(userObject.name)
                .password(userObject.password)
                .roles(userObject.role).build();
    }

    private static class UserObject {
        private final String name;
        private final String password;
        private final String role;

        public UserObject(String name, String password, String role) {
            this.name = name;
            this.password = password;
            this.role = role;
        }
    }
}