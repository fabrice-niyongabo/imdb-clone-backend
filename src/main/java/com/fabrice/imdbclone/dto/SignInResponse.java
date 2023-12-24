package com.fabrice.imdbclone.dto;

import lombok.Data;
@Data
public class SignInResponse extends JWTAuthResponse{
    UserDetailsResponse userDetails;
}
