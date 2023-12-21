package com.fabrice.imdbclone.controllers;

import com.fabrice.imdbclone.services.AuthenticationService;
import com.fabrice.imdbclone.dto.JWTAuthResponse;
import com.fabrice.imdbclone.dto.RefeshTokenRequest;
import com.fabrice.imdbclone.dto.SigninRequest;
import com.fabrice.imdbclone.dto.SignupRequest;
import com.fabrice.imdbclone.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
//@CrossOrigin //by default it allows all origins
@CrossOrigin(origins = "*")

public class AuthController {
    private  final AuthenticationService authenticationService;

    @PostMapping("/signup")
    public ResponseEntity<User> signup(@RequestBody SignupRequest signupRequest){
        return ResponseEntity.ok(authenticationService.signup(signupRequest));
    }


    @PostMapping("/signin")
    public ResponseEntity<JWTAuthResponse> signup(@RequestBody SigninRequest signinRequest){
        return ResponseEntity.ok(authenticationService.signin(signinRequest));
    }

    @PostMapping("/refresh")
    public ResponseEntity<JWTAuthResponse> refresh(@RequestBody RefeshTokenRequest refeshTokenRequest){
        return ResponseEntity.ok(authenticationService.refreshToken(refeshTokenRequest));
    }
}
