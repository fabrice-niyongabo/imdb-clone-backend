package com.fabrice.imdbclone.repositories;

import com.fabrice.imdbclone.models.Watchlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface WatchlistRepository extends JpaRepository<Watchlist, Long> {
    List<Watchlist> findByUserId(Long userId);

    boolean existsByUserIdAndMovieId(Long userId, Long movieId);

    //deleting by adding custom query
    //@Transactional
    //@Modifying
    //@Query("DELETE FROM Watchlist WHERE userId= :userId AND movieId= :movieId")
    //void deleteByUserIdAndMovieId(Long userId, Long movieId);


    //@Transactional annotation ensures the operation is wrapped within a transaction
    //and is required by @Modifying
    @Transactional
    //specifying that this is an update or delete command
    @Modifying
    void deleteByUserIdAndMovieId(Long userId, Long movieId);
}
