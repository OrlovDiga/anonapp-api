package anonapp.api.controller;

import anonapp.api.dto.MailDTO;
import anonapp.email.sender.EmailServiceImpl;
import anonapp.util.PasswordGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Orlov Diga
 */
@RestController
@RequestMapping(value = "/reduction", produces = "application/json")
public class ReductionController {

    private static final Logger LOG = LoggerFactory.getLogger(ReductionController.class);

    private static final int PASSWORD_LENGTH = 8;
    private static final String INTRODUCTORY_MESSAGE = "Hello, this is your new password: ";

    private final EmailServiceImpl emailService;
    private final PasswordGenerator generator;

    @Autowired
    public ReductionController(EmailServiceImpl emailService) {
        this.emailService = emailService;
        this.generator = new PasswordGenerator.PasswordGeneratorBuilder()
                .useDigits(true)
                .useLower(true)
                .useUpper(true)
                .build();
    }

    @PostMapping
    public void changePassword(@RequestBody MailDTO mailDTO) {
        LOG.info("Send email with new password to {}.", mailDTO.getMail());
        emailService.sendSimpleMessage(
                mailDTO.getMail(),
                "Change password to AnonApp",
                INTRODUCTORY_MESSAGE + generator.generate(PASSWORD_LENGTH));
    }
}
