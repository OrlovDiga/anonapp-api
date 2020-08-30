package anonapp;

import anonapp.data.service.impl.UserServiceImpl;
import anonapp.domain.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

/**
 * The main class that launches the application.
 *
 * @author Orlov Diga
 */
@SpringBootApplication
public class AnonApplication {

    private PasswordEncoder passwordEncoder;
    private UserServiceImpl userService;

    @Autowired
    public AnonApplication(PasswordEncoder passwordEncoder, UserServiceImpl userService) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }

    public static void main(String[] args) {
        SpringApplication.run(AnonApplication.class, args);
    }

    @Bean
    @Transactional
    public CommandLineRunner execute() {
        return args -> {
            User user = new User();
            user.setLogin("lol@kek.com");
            user.setPassword(passwordEncoder.encode("1024"));
            user.setUsername("lol");
            userService.create(user);

            User user1 = new User();
            user1.setLogin("user@java.org");
            user1.setPassword(passwordEncoder.encode("1234"));
            user1.setUsername("user");

            userService.create(user1);
        };
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
