package com.movies.reviewsservice.controller;


import com.movies.reviewsservice.dto.ReviewWithMovieDto;
import com.movies.reviewsservice.model.Review;
import com.movies.reviewsservice.request.ReviewRequest;
import com.movies.reviewsservice.service.IReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/reviews")
public class ReviewController {

    private final IReviewService reviewService;



    @PostMapping
    public ResponseEntity<Review> createReview(@RequestBody ReviewRequest request,
                                               Authentication authentication) {
        Long userId = (Long) authentication.getPrincipal(); // or extract from JWT claims
        System.out.println("user id:" + userId);
        Review review = reviewService.createReview(userId, request);
        return ResponseEntity.ok(review);
    }

    @GetMapping("/movie/{movieId}")
    public ResponseEntity<List<ReviewWithMovieDto>> getReviewsByMovie(@PathVariable Long movieId) {
        return ResponseEntity.ok(reviewService.getReviewsByMovie(movieId));
    }

    @GetMapping("/review/{reviewId}")
    public ResponseEntity<Review> getReviewById(@PathVariable Long reviewId) {
        return ResponseEntity.ok(reviewService.getReviewById(reviewId));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Review>> getReviewsByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(reviewService.getReviewsByUser(userId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Review> updateReview(@PathVariable Long id,
                                               @RequestBody ReviewRequest request,
                                               Authentication authentication) {
        Long userId = (Long) authentication.getPrincipal();
        return ResponseEntity.ok(reviewService.updateReview(id, userId, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id,
                                             Authentication authentication) {
        Long userId = (Long) authentication.getPrincipal();
        reviewService.deleteReview(id, userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("all")
    public ResponseEntity<List<Review>> getAllReviews() {
        return ResponseEntity.ok(reviewService.getAllReviews());
    }

}
