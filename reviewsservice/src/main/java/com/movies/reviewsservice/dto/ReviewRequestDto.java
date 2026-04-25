package com.movies.reviewsservice.dto;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ReviewRequestDto {

    private Long movieId;
    private double rating;
    private String comment;
}
