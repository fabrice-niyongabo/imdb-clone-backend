package com.fabrice.imdbclone.dto;

import com.fabrice.imdbclone.models.User;
import lombok.Data;

@Data
public class SignupResponse extends JWTAuthResponse{
    User userDetails;
}
