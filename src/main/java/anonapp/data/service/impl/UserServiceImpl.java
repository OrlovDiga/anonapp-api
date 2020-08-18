package anonapp.data.service.impl;

import anonapp.data.repo.UserRepo;
import anonapp.data.service.UserService;
import anonapp.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @author Orlov Diga
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;

    @Autowired
    public UserServiceImpl(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public User create(User user) {
        user.setCreated(LocalDateTime.now());
        user.setUpdated(LocalDateTime.now());
        return userRepo.save(user);
    }

    public void remove(Long id) {
        userRepo.deleteById(id);
    }

    public User changePassword(User user) {
        return userRepo.save(user);
    }

    public User findById(Long id) {
        return userRepo.findById(id).get();
    }

    @Override
    public User findByUsername(String username) {
        return userRepo.findByUsername(username);
    }

    @Override
    public boolean existsByLogin(String login) {
        return userRepo.existsByLogin(login);
    }
}
