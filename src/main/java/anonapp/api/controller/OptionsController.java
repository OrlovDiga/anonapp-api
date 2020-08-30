package anonapp.api.controller;

import anonapp.api.dto.ChangePasswordDTO;
import anonapp.api.dto.UserDTO;
import anonapp.data.service.impl.UserServiceImpl;
import anonapp.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * This class provides methods for changing user data.
 *
 * @author Orlov Diga
 */
@RestController
@RequestMapping(value = "/api/options")
public class OptionsController {

    private static final Logger LOG = LoggerFactory.getLogger(OptionsController.class);

    private final UserServiceImpl userService;
    private final PasswordEncoder encoder;

    @Autowired
    public OptionsController(UserServiceImpl userService, PasswordEncoder encoder) {
        this.userService = userService;
        this.encoder = encoder;
    }

    /**
     * This method is needed to change the user's password by the user himself in the database.
     * Change user password field in the database.
     *
     * @param userDTO contains needs information for change password.
     *                It has annotation {@link RequestBody @RequestBody}
     *                this means deserialization from json.
     *
     * @param user is data entity from database table.
     *             It is necessary to compare the data with userDTO
     *             and save with a new password to the database.
     *
     * @return {@link ResponseEntity<UserDTO>} response.
     * If the password change was successful, the status code {@link HttpStatus} OK(200)
     * is stored in the header of this, otherwise BED_REQUEST(400).
     */
    @PostMapping(value = "changePswd")
    public ResponseEntity<UserDTO> changePassword(@RequestBody ChangePasswordDTO userDTO,
                                                  @AuthenticationPrincipal User user) {

        if (userDTO.getNewPassword().equals(userDTO.getMatchingNewPassword())
         && user != null
         && encoder.matches(userDTO.getOldPassword(), user.getPassword())) {
            LOG.info("Password for {} was changed.", user.getUsername());

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
