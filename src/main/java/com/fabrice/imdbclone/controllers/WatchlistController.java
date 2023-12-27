package com.fabrice.imdbclone.controllers;

import com.fabrice.imdbclone.dto.WatchlistRequest;
import com.fabrice.imdbclone.exceptions.BadRequestException;
import com.fabrice.imdbclone.exceptions.NotFoundException;
import com.fabrice.imdbclone.models.User;
import com.fabrice.imdbclone.models.Watchlist;
import com.fabrice.imdbclone.repositories.UserRepository;
import com.fabrice.imdbclone.repositories.WatchlistRepository;
import com.fabrice.imdbclone.utils.CustomMethods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.List;

@RestController
@RequestMapping("/api/v1/watchlist")
@CrossOrigin(value = "*")
public class WatchlistController {
    @Autowired
    private WatchlistRepository watchlistRepository;
    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public List<Watchlist> getUserWatchlist() {
        CustomMethods customMethods = new CustomMethods();
        String email = customMethods.returnLoggedInUsername();
        User user = userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("User not found"));

        return watchlistRepository.findByUserId(user.getId());
    }

    @PostMapping
    public Watchlist pushToUserWatchlist(@RequestBody WatchlistRequest watchlistRequest) {
        CustomMethods customMethods = new CustomMethods();
        String email = customMethods.returnLoggedInUsername();
        User user = userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("User not found"));

        if (!watchlistRepository.existsByUserIdAndMovieId(
                user.getId(),
                watchlistRequest.getMovieId())
        ) {
            return watchlistRepository.save(new Watchlist(
                            user.getId(),
                            watchlistRequest.getMovieId(),
                            watchlistRequest.getTitle(),
                            watchlistRequest.getBackdrop_path(),
                            watchlistRequest.getPoster_path(),
                            watchlistRequest.getMovie_type(),
                            watchlistRequest.getRelease_date(),
                            watchlistRequest.getOverview()
                    )
            );
        } else {
            throw new BadRequestException("Movie already exists within your watchlist");
        }

    }
}
