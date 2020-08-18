package anonapp.api.controller;

import anonapp.api.dto.UserDTO;
import anonapp.data.service.impl.UserServiceImpl;
import anonapp.domain.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Orlov Diga
 */
@RestController
@RequestMapping(path = "/register")
public class RegisterController {

    private UserServiceImpl userService;
    private ModelMapper modelMapper;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public RegisterController(UserServiceImpl userService,
                              ModelMapper modelMapper,
                              PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping
    public ResponseEntity<UserDTO> register(@RequestBody UserDTO userDTO) {
        return new ResponseEntity<>(
                convertToDTO(userService.create(convertToEntity(userDTO))),
                HttpStatus.CREATED
        );
    }

    private User convertToEntity(UserDTO userDTO) {
        return new User.UserBuilder()
                .login(userDTO.getUsername())
                .username(userDTO.getUsername())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .build();
    }

    private UserDTO convertToDTO(User user) {
        return new UserDTO.UserDtoBuilder()
                .id(user.getId())
                .username(user.getUsername())
                .build();
    }
}
