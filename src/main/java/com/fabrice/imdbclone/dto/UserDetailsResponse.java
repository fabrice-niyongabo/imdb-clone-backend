package com.fabrice.imdbclone.dto;

import com.fabrice.imdbclone.models.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data 
public class UserDetailsResponse {
    private Long id;
    private String names;
    private String email;
    private Role role;

    public UserDetailsResponse(Long id, String names, String email, Role role) {
        this.id = id;
        this.names = names;
        this.email = email;
        this.role = role;
    }
}
