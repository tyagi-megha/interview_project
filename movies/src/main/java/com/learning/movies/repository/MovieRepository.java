package com.learning.movies.repository;

import com.learning.movies.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    List<Movie> findByRatingGreaterThan(double rating);
    List<Movie> findByTitle(String title);
    List<Movie> findByDirector(String director);
    List<Movie> findByRating(double rating);
    boolean existsByTitleAndDirector(String title, String director);
}
