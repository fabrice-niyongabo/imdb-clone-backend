package com.fabrice.imdbclone.dto;

import lombok.Data;

@Data
public class WatchlistRequest {
    private Long movieId;
    private String backdrop_path;
    private String movie_type;
    private String overview;
    private String poster_path;
    private String release_date;
    private String title;
}
