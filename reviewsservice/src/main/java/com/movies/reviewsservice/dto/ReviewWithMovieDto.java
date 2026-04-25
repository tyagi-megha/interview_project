package com.movies.reviewsservice.dto;


import com.movies.reviewsservice.model.Review;
import lombok.Data;

@Data
public class ReviewWithMovieDto {

    private Long reviewId;
    private Long userId;
    private Long movieId;
    private double rating; //rating by user
    private String comment;

    //movie metadata
    private String movieTitle;
    private String movieDirector;
    private double movieRating; //rating for movie

    public ReviewWithMovieDto(Review review, MovieDto movie){
        this.reviewId = review.getId();
        this.userId = review.getUserId();
        this.movieId = review.getMovieId();
        this.rating = review.getRating();
        this.comment = review.getComment();

        this.movieTitle = movie.getTitle();
        this.movieDirector = movie.getDirector();
        this.movieRating = movie.getRating();
    }
}
