package anonapp.api.controller;

import anonapp.api.dto.ChangePasswordDTO;
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
 * @author Orlov Diga
 */
@RestController
@RequestMapping(value = "/api/options")
public class OptionsController {

    private final UserServiceImpl userService;
    private final PasswordEncoder encoder;

    @Autowired
    public OptionsController(UserServiceImpl userService, PasswordEncoder encoder) {
        this.userService = userService;
        this.encoder = encoder;
    }

    @PostMapping(value = "changePswd")
    public ResponseEntity<UserDTO> changePassword(@RequestBody ChangePasswordDTO userDTO) {
        User user = userService.findByUsername(userDTO.getUsername());

        if (userDTO.getNewPassword().equals(userDTO.getMatchingNewPassword())
         && user != null
         && encoder.encode(userDTO.getOldPassword()).equals(user.getPassword())) {
            user.setPassword(encoder.encode(userDTO.getNewPassword()));
            user = userService.changePassword(user);
            return new ResponseEntity<>(
                    new UserDTO.UserDtoBuilder()
                            .id(user.getId())
                            .username(user.getUsername())
                            .build(),
                    HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}
