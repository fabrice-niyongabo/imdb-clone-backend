package com.fabrice.imdbclone.services;

import com.fabrice.imdbclone.dto.JWTAuthResponse;
import com.fabrice.imdbclone.dto.RefeshTokenRequest;
import com.fabrice.imdbclone.dto.SigninRequest;
import com.fabrice.imdbclone.dto.SignupRequest;
import com.fabrice.imdbclone.models.User;

public interface AuthenticationService {
    User signup(SignupRequest signupRequest);
    JWTAuthResponse signin(SigninRequest signinRequest);
    JWTAuthResponse refreshToken(RefeshTokenRequest refeshTokenRequest);
}
