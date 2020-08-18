package anonapp.data.service;

import anonapp.domain.User;

/**
 * @author Orlov Diga
 */
public interface UserService {
    User create(User user);

    void remove(Long id);

    User changePassword(User user);

    User findById(Long id);

    User findByUsername(String username);

    boolean existsByLogin(String login);
}
