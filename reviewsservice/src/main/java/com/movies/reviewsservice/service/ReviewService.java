package com.movies.reviewsservice.service;

import com.movies.reviewsservice.dto.MovieDto;
import com.movies.reviewsservice.dto.ReviewWithMovieDto;
import com.movies.reviewsservice.integration.MovieServiceClient;
import com.movies.reviewsservice.model.Review;
import com.movies.reviewsservice.repository.ReviewRepository;
import com.movies.reviewsservice.request.ReviewRequest;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReviewService implements IReviewService{

    private final ReviewRepository reviewRepository;

    private final MovieServiceClient movieServiceClient;

    @Override
    public Review createReview(Long userId, ReviewRequest request) {

        MovieDto movie = movieServiceClient.getMovieById(request.getMovieId());

        return Optional.ofNullable(movie)
                .map( m -> {
                    Review review = new Review();
                    review.setUserId(userId);
                    review.setMovieId(m.getId());
                    review.setRating(request.getRating());
                    review.setComment(request.getComment());
                    return reviewRepository.save(review);
                })
                .orElseThrow( () -> new EntityNotFoundException("Movie not found"));
    }

    @Override
    public List<ReviewWithMovieDto> getReviewsByMovie(Long movieId) {
      //  MovieDto movie = movieServiceClient.getMovieById(movieId);
      //  return reviewRepository.findByMovieId(movieId);
        return Optional.ofNullable(movieServiceClient.getMovieById(movieId))
                .map(movie -> reviewRepository.findByMovieId(movieId).stream()
                        .map(review -> new ReviewWithMovieDto(review, movie))
                        .toList()
                )
                .orElseThrow(() -> new EntityNotFoundException("Movie not found with id: " + movieId));


    }

    @Override
    public Review getReviewById(Long reviewId) {
        return reviewRepository.findById(reviewId)
                .orElseThrow(() -> new EntityNotFoundException("Review with id "+ reviewId + " does not exist"));
    }

    @Override
    public List<Review> getReviewsByUser(Long userId) {
        return reviewRepository.findByUserId(userId);

    }

    @Override
    public Review updateReview(Long id, Long userId, ReviewRequest request) {

      /*  return reviewRepository.findById(id).map(
                existingReview -> {
                    existingReview.setRating(request.getRating());
                    existingReview.setComment(request.getComment());
                    return reviewRepository.save(existingReview);
                }
        ).orElseThrow(() -> new EntityNotFoundException("Review not found")); */

        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Review not found"));
        if (!review.getUserId().equals(userId)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Unauthorized update");
        }
        review.setRating(request.getRating());
        review.setComment(request.getComment());
        return reviewRepository.save(review);
    }

    @Override
    public void deleteReview(Long id, Long userId) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Review not found"));
        if (!review.getUserId().equals(userId)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Unauthorized delete");
        }
        reviewRepository.delete(review);
    }

    @Override
    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }
}
