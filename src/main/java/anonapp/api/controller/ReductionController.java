package anonapp.api.controller;

import anonapp.api.dto.MailDTO;
import anonapp.data.service.impl.UserServiceImpl;
import anonapp.domain.User;
import anonapp.email.sender.EmailServiceImpl;
import anonapp.util.PasswordGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * This class provides service for changing forgot user password.
 *
 * @author Orlov Diga
 */
@RestController
@RequestMapping(value = "/reduction", produces = "application/json")
public class ReductionController {

    private static final Logger LOG = LoggerFactory.getLogger(ReductionController.class);

    private static final int PASSWORD_LENGTH = 8;
    private static final String INTRODUCTORY_MESSAGE = "Hello, this is your new password: ";

    private final EmailServiceImpl emailService;
    private final UserServiceImpl userService;
    private final PasswordGenerator generator;
    private final PasswordEncoder encoder;

    @Autowired
    public ReductionController(EmailServiceImpl emailService, UserServiceImpl userService, PasswordEncoder encoder) {
        this.emailService = emailService;
        this.userService = userService;
        this.encoder = encoder;
        this.generator = new PasswordGenerator.PasswordGeneratorBuilder()
                .useDigits(true)
                .useLower(true)
                .useUpper(true)
                .build();
    }

    /**
     * This method is needed to change the user's password when the user has forgotten the password.
     * Change user password field in the database.
     *
     * @param mailDTO this param has annotation  {@link RequestBody @RequestMapping},
     *               which means that this param will be deserialization from request json data.
     *
     * @return {@link org.springframework.http.ResponseEntity} with a specific status code.
     * if status code {@link org.springframework.http.HttpStatus} is OK(200),
     * it means that the password change was successful.
     * if status code {@link org.springframework.http.HttpStatus} is BED_REQUEST(400),
     * it means password change was failed.
     */
    @PostMapping
    public void changePassword(@RequestBody MailDTO mailDTO) {
        if (!mailDTO.getMail().isEmpty()) {
            User user = userService.findByUsername(mailDTO.getMail());

            if (user != null) {
                String password = generator.generate(PASSWORD_LENGTH);
                user.setPassword(encoder.encode(password));
                userService.changePassword(user);

                LOG.info("Send email with new password ({}) to {}.", password, mailDTO.getMail());
                emailService.sendSimpleMessage(
                        mailDTO.getMail(),
                        "Change password to AnonApp",
                        INTRODUCTORY_MESSAGE + password);
            }
        }

    }
}
