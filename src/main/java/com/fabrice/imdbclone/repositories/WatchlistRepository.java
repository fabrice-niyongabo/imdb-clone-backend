package com.fabrice.imdbclone.repositories;

import com.fabrice.imdbclone.models.Watchlist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WatchlistRepository extends JpaRepository<Watchlist, Long> {
}
