package com.learning.movies.request;

import lombok.Data;

@Data
public class UpdateMovieRequest {
    private Long id;

    private String title;
    private String director;
    private double rating;
}
