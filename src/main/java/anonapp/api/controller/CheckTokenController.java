package anonapp.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * This class needs  checks the validity of the token.
 *
 * @author Orlov Diga
 */
@RestController
@RequestMapping("/api/check")
public class CheckTokenController {

    /**
     * This method checks the validity of the token.
     *
     * Request header must contains field token for authentication.
     *
     * @return {@link org.springframework.http.ResponseEntity} http response with status,
     * that will must be checked.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void checkToken() {
    }
}
