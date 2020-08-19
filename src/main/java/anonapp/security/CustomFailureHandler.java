package anonapp.security;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Orlov Diga
 */
public class CustomFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException e) throws IOException, ServletException {

        response.setStatus(401);
        /*response.encodeURL("/mobile/app/sign-in?loginFailure=true");
        response.encodeRedirectURL("/mobile/app/sign-in?loginFailure=true");*/
        System.out.println("Incorrect data for login");
    }
}
