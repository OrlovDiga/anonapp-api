package anonapp.api.controller;

import anonapp.api.dto.UserDTO;
import anonapp.data.service.impl.UserServiceImpl;
import anonapp.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The class provides a service for registering a new user.
 *
 * @author Orlov Diga
 */
@RestController
@RequestMapping(path = "/register")
public class RegisterController {

    private UserServiceImpl userService;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public RegisterController(UserServiceImpl userService,
                              PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * This method register new user - save to database table user entity.
     *
     * @param userDTO contains needs information for registration new user.
     *                It has annotation {@link RequestBody @RequestBody}
     *                this means deserialization from request json data.
     *
     * @return {@link ResponseEntity<UserDTO>} response.
     * If user creation was successful, then the response payload contains
     * a userDTO in json format  and the response header contains the status code CREATED(201),
     * otherwise response payload contains a null,
     * and the response header contains the status code BAD_REQUEST(400).
     */
    @PostMapping
    public ResponseEntity<UserDTO> register(@RequestBody UserDTO userDTO) {
        if (userDTO.getPassword().equals(userDTO.getMatchingPassword()) &&
            userService.findByUsername(userDTO.getUsername()) == null
        ) {
            return new ResponseEntity<>(
                    convertToDTO(userService.create(convertToEntity(userDTO))),
                    HttpStatus.CREATED
            );
        }

        return new ResponseEntity<>(
                null,
                HttpStatus.BAD_REQUEST
        );
    }

    /**
     * This method convert {@link UserDTO} to {@link User}.
     *
     * @param userDTO - User Data Transfer Object contains information for create user entity.
     *
     * @return {@link User} user entity.
     */
    private User convertToEntity(UserDTO userDTO) {
        return new User.UserBuilder()
                .login(userDTO.getUsername())
                .username(userDTO.getUsername())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .build();
    }

    /**
     * This method convert {@link User} to {@link UserDTO}.
     *
     * @param user - user entity contains information for create userDTO.
     *
     * @return {@link UserDTO} userDTO - User Data Transfer Object for send to the client.
     */
    private UserDTO convertToDTO(User user) {
        return new UserDTO.UserDtoBuilder()
                .id(user.getId())
                .username(user.getUsername())
                .build();
    }
}
