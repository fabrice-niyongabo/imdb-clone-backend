package com.fabrice.imdbclone.services.impl;

import com.fabrice.imdbclone.dto.*;
import com.fabrice.imdbclone.exceptions.BadRequestException;
import com.fabrice.imdbclone.exceptions.NotFoundException;
import com.fabrice.imdbclone.models.Role;
import com.fabrice.imdbclone.models.User;
import com.fabrice.imdbclone.repositories.UserRepository;
import com.fabrice.imdbclone.services.AuthenticationService;
import com.fabrice.imdbclone.services.JWTService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;

    public User signup(SignupRequest signupRequest){
        User user = new User();
        user.setNames(signupRequest.getNames());
        user.setEmail(signupRequest.getEmail());
        user.setRole(Role.USER); // by default, all users created will have USER Role

        //hash user password
        user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));

        //save our user to db
      return userRepository.save(user);
    }

    public SignInResponse signin(SigninRequest signinRequest){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signinRequest.getEmail(),signinRequest.getPassword()));
        var user  = userRepository.findByEmail(signinRequest.getEmail())
                .orElseThrow(() -> new BadRequestException("Invalid email or password"));
        var jwt  =  jwtService.generateToken(user);
        var refreshToken  =  jwtService.generateRefreshToken(new HashMap<>(),user);

//        JWTAuthResponse jwtAuthResponse =  new JWTAuthResponse();
//        jwtAuthResponse.setToken(jwt);
//        jwtAuthResponse.setRefreshToken(refreshToken);


        //response with user details
        SignInResponse signinResponse = new SignInResponse();
        //user details object
        UserDetailsResponse userDetails = new UserDetailsResponse(
                user.getId(),
                user.getNames(),
                user.getEmail(),
                user.getRole()
        );

        //populate the user singIn response with its values
        signinResponse.setUserDetails(userDetails);
        signinResponse.setToken(jwt);
        signinResponse.setRefreshToken(refreshToken);

        return signinResponse;
    }

     public JWTAuthResponse refreshToken(RefeshTokenRequest refeshTokenRequest){
        String userEmail = jwtService.extractUsername(refeshTokenRequest.getToken());
        User user = userRepository.findByEmail(userEmail).orElseThrow(() -> new NotFoundException("User with such token can not be found"));
        if(jwtService.isTokenValid(refeshTokenRequest.getToken(), user)){
            var jwt = jwtService.generateToken(user);
            var updatedRefreshToken  =  jwtService.generateRefreshToken(new HashMap<>(),user);

            JWTAuthResponse jwtAuthResponse =  new JWTAuthResponse();

            jwtAuthResponse.setToken(jwt);
            jwtAuthResponse.setRefreshToken(updatedRefreshToken);

            return jwtAuthResponse;

        }
        return null;
     }
}
