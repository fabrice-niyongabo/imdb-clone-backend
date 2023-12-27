package com.fabrice.imdbclone.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Entity
@Table(name = "watchlists")
public class Watchlist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private Long movieId;
    private String title;
    private String backdrop_path;
    private String poster_path;
    private String movie_type;
    private String release_date;
    @Column(columnDefinition = "TEXT")
    private String overview;

    public Watchlist(Long userId, Long movieId, String title, String backdrop_path, String poster_path, String movie_type, String release_date, String overview) {
        this.userId = userId;
        this.movieId = movieId;
        this.title = title;
        this.backdrop_path = backdrop_path;
        this.poster_path = poster_path;
        this.movie_type = movie_type;
        this.release_date = release_date;
        this.overview = overview;
    }

    public Watchlist() {

    }
}
