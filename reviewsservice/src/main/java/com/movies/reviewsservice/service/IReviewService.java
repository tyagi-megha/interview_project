package com.movies.reviewsservice.service;

import com.movies.reviewsservice.dto.ReviewWithMovieDto;
import com.movies.reviewsservice.model.Review;
import com.movies.reviewsservice.request.ReviewRequest;

import java.util.List;

public interface IReviewService {

    public Review createReview(Long userId, ReviewRequest request);

    public List<ReviewWithMovieDto> getReviewsByMovie(Long movieId);

    public Review getReviewById(Long reviewId);

    public List<Review> getReviewsByUser(Long userId);

    public Review updateReview(Long id, Long userId, ReviewRequest request);

    public void deleteReview(Long id, Long userId);

    public List<Review> getAllReviews();
}
