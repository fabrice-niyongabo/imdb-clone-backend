package com.fabrice.imdbclone.config;


import com.fabrice.imdbclone.dto.ErrorResponse;
import com.fabrice.imdbclone.exceptions.UnauthorizedException;
import com.fabrice.imdbclone.models.Role;
import com.fabrice.imdbclone.services.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;



@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
    private final JWTAuthenticationFilter jwtAuthenticationFIlter;
    private  final UserService userService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.csrf(AbstractHttpConfigurer::disable)
                //specifying the apis that will be accessible without providing a token
                .authorizeHttpRequests(request -> request.requestMatchers("/api/v1/auth/**")
                        .permitAll()
                        //restricting /api/v1/admin to be only accessed by users with ADMIN role
                        .requestMatchers("/api/v1/admin").hasAnyAuthority(Role.ADMIN.name())
                        //restricting /api/v1/user to be only accessed by users with USER role
                        .requestMatchers("/api/v1/user").hasAnyAuthority(Role.USER.name())
                        //adding security to all the rest of the endpoints
                        .anyRequest().authenticated())
                //disabling default spring security sesssion because we are not storing anything.
                .sessionManagement(manager -> manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider()).addFilterBefore(
                        jwtAuthenticationFIlter, UsernamePasswordAuthenticationFilter.class
                )
                //handling errors that my occur due to unauthorized
                .exceptionHandling(customizer -> customizer
                        //handling invalid token
                        .authenticationEntryPoint((request, response, authException) -> {
                            response.setStatus(HttpStatus.UNAUTHORIZED.value());
                            response.setContentType("application/json");
                            String message = "Invalid token";
                            int statusCode =  HttpStatus.UNAUTHORIZED.value();
                            response.getWriter().write("{ \"message\": \""+message+"\",\"statusCode\":\""+statusCode+"\"}");
                        })
                        //handling access denied scenario  => trying to access private stuffs
                        .accessDeniedHandler((request, response, accessDeniedException) -> {
                            response.setStatus(HttpStatus.FORBIDDEN.value());
                            response.setContentType("application/json");
                            String message = "You do not have access to this route.";
                            int statusCode =  HttpStatus.FORBIDDEN.value();
                            response.getWriter().write("{ \"message\": \""+message+"\",\"statusCode\":\""+statusCode+"\"}");
                        }));
        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userService.userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return  authenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception{
        return config.getAuthenticationManager();
    }
}
