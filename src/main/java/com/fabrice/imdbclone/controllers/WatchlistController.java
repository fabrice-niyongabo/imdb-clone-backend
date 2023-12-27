package com.fabrice.imdbclone.controllers;

import com.fabrice.imdbclone.exceptions.NotFoundException;
import com.fabrice.imdbclone.models.User;
import com.fabrice.imdbclone.models.Watchlist;
import com.fabrice.imdbclone.repositories.UserRepository;
import com.fabrice.imdbclone.repositories.WatchlistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/watchlist")
public class WatchlistController {
    @Autowired
    private WatchlistRepository watchlistRepository;
    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public List<Watchlist> getUserWatchlist() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // Get the username
        String username = authentication.getName();
        User user = userRepository.findByEmail(username).orElseThrow(() -> new NotFoundException("User not found"));

        return watchlistRepository.findByUserId(user.getId());
    }
}
