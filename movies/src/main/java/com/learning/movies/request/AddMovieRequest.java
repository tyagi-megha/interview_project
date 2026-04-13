package com.learning.movies.request;

import lombok.Data;

@Data
public class AddMovieRequest {
    private Long id;

    private String title;
    private String director;
    private double rating;
}
