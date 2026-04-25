package com.movies.reviewsservice.request;

import lombok.Data;

@Data
public class ReviewRequest {

    private Long id;

    private Long userId;   // from JWT claims
    private Long movieId;  // movie being reviewed
    private double rating;    // 1.0-10.0
    private String comment;
}
