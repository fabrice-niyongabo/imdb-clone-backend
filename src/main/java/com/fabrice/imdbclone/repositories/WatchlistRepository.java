package com.fabrice.imdbclone.repositories;

import com.fabrice.imdbclone.models.Watchlist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface WatchlistRepository extends JpaRepository<Watchlist, Long> {
   List<Watchlist> findByUserId(Long userId);
}
