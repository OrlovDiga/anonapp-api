package anonapp.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.RememberMeAuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.json.Json;
import javax.json.JsonObject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

/**
 * This class provides main application security and routing for registration, authentication.
 *
 * @author Orlov Diga
 */
@Configuration
@EnableWebSecurity
@Order(2)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private UserDetailsServiceImpl userDetailsServiceImpl;
    private CustomTokenBasedRememberMeService tokenBasedRememberMeService;
    private RememberMeAuthenticationProvider rememberMeAuthenticationProvider;

    @Autowired
    public WebSecurityConfig(UserDetailsServiceImpl userDetailsServiceImpl,
                             CustomTokenBasedRememberMeService tokenBasedRememberMeService,
                             RememberMeAuthenticationProvider rememberMeAuthenticationProvider) {
        this.userDetailsServiceImpl = userDetailsServiceImpl;
        this.tokenBasedRememberMeService = tokenBasedRememberMeService;
        this.rememberMeAuthenticationProvider = rememberMeAuthenticationProvider;
    }

    @Override protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .authorizeRequests()
                .antMatchers("/socket").permitAll()
                .antMatchers("/socket/**").permitAll()
                .antMatchers("/resources/**").permitAll()
                .antMatchers("/register").permitAll()
                .antMatchers("/reduction").permitAll()
                .anyRequest().authenticated()
                .and().apply(new JSONLoginConfigurer<>())
                .loginPage("/authenticate")
                .successHandler(new CustomSuccessHandler())
                .failureHandler(new CustomFailureHandler())
                .permitAll().and()
                .rememberMe().rememberMeServices(tokenBasedRememberMeService);
    }

    @Override protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsServiceImpl)
                .passwordEncoder(bCryptPasswordEncoder());
        auth.authenticationProvider(rememberMeAuthenticationProvider);
    }

    @Bean
    @Override public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /**
     * This is the a configurer that forces the JSONAuthenticationFilter.
     * based on org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer
     */
    private class JSONLoginConfigurer<H extends HttpSecurityBuilder<H>> extends
            AbstractAuthenticationFilterConfigurer<H, JSONLoginConfigurer<H>, UsernamePasswordAuthenticationFilter> {

        public JSONLoginConfigurer() {
            super(new JSONAuthenticationFilter(), null);
        }

        @Override
        public JSONLoginConfigurer<H> loginPage(String loginPage) {
            return super.loginPage(loginPage);
        }

        @Override
        protected RequestMatcher createLoginProcessingUrlMatcher(String loginProcessingUrl) {
            return new AntPathRequestMatcher(loginProcessingUrl, "POST");
        }

    }

    /**
     * This is the filter that actually handles the json
     */
    private class JSONAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

        protected String obtainPassword(JsonObject obj) {
            return obj.getString(getPasswordParameter());
        }

        protected String obtainUsername(JsonObject obj) {
            return obj.getString(getUsernameParameter());
        }

        @Override
        public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
                throws AuthenticationException {
            System.out.println(request.getContentType());
            if (!request.getContentType().toLowerCase().contains("application/json")) {
                System.out.println(request.getContentType());

                return super.attemptAuthentication(request, response);
            }

            try (BufferedReader reader = request.getReader()) {
                JsonObject obj = Json.createReader(reader).readObject();

                String username = obtainUsername(obj);
                String password = obtainPassword(obj);

                System.out.println(username);
                System.out.println(password);

                UsernamePasswordAuthenticationToken authRequest =
                        new UsernamePasswordAuthenticationToken(username, password);

                return this.getAuthenticationManager().authenticate(authRequest);
            } catch (IOException ex) {
                throw new AuthenticationServiceException("Parsing Request failed", ex);
            }
        }
    }
}
