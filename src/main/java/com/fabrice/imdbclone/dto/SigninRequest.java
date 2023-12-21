package com.fabrice.imdbclone.dto;


import lombok.Data;

@Data // for generating getters and setters
public class SigninRequest {
    private  String email;
    private String password;
}
