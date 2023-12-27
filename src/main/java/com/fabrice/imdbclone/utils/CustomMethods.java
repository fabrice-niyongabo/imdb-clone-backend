package com.fabrice.imdbclone.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

@Controller
public class CustomMethods {
    public String returnLoggedInUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // Get and return the username
        return authentication.getName();
    }
}
