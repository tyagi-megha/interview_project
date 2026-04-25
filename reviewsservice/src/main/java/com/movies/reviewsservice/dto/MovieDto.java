package com.movies.reviewsservice.dto;

import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Data
public class MovieDto {

    private Long id;

    private String title;
    private String director;
    private double rating;
}

