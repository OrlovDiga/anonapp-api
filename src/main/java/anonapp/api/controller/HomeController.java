package anonapp.api.controller;

import anonapp.service.stomp.ChatConnectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Orlov Diga
 */
@Profile("dev")
@RestController()
@RequestMapping(path = "/api")
public class HomeController {

    List<Long> userIdList = new ArrayList<>();
    private ChatConnectService connectService;

    @Autowired
    public HomeController(ChatConnectService connectService) {
        this.connectService = connectService;
    }

    @PostMapping("/lol")
    public ResponseEntity<String> test() {
        return new ResponseEntity<>("Hello, World", HttpStatus.OK);
    }


}
