package anonapp.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Orlov Diga
 */
@RestController
@RequestMapping("/api/check")
public class CheckTokenController {

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void checkToken() {
    }
}
