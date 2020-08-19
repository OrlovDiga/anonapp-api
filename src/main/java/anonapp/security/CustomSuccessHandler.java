package anonapp.security;


import anonapp.domain.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Orlov Diga
 */
public class CustomSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        JsonObjectBuilder userBuilder = Json.createObjectBuilder();

        User user = (User) authentication.getPrincipal();
        userBuilder.add("id", user.getId());
        userBuilder.add("username", authentication.getName());
        response.setContentType("application/json");
        response.getWriter().write(userBuilder.build().toString());
        response.setStatus(200);
    }
}
