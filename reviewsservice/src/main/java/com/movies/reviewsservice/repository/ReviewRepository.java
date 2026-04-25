package com.movies.reviewsservice.repository;

import com.movies.reviewsservice.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByMovieId(Long movieId);
    List<Review> findByUserId(Long userId);
}
