package anonapp.api.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * In development...
 *
 * @author Orlov Diga
 */
@RestController()
@RequestMapping(path = "/api/home")
public class HomeController {

   /**
    * In development...
    */
    @PostMapping(value = "/likes")
    public void getMutuallyLikedUsers() {

    }


}
