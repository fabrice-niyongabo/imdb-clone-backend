package com.fabrice.imdbclone.dto;


import lombok.Data;

@Data //lambok annotation for getting setters&getters
public class SignupRequest {
    //this is how the signup request will be formatted
    private String names;
    private String email;
    private String password;
}
