package com.fabrice.imdbclone.models;

import jakarta.persistence.*;
import lombok.Data;

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
    private String overview;
}
