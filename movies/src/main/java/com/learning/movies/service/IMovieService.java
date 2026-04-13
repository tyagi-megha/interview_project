package com.learning.movies.service;

import com.learning.movies.model.Movie;
import com.learning.movies.request.AddMovieRequest;
import com.learning.movies.request.UpdateMovieRequest;

import java.util.List;

public interface IMovieService {
    Movie addMovies(AddMovieRequest request);
    Movie updateMovies(UpdateMovieRequest request, Long movieId);
    Movie getMovieById(Long movieId);
    void deleteMovieById(Long movieId);

    List<Movie> getAllMovies();
    List<Movie> getMovieByTitle(String title);
    List<Movie> getMovieByDirector(String director);
    List<Movie> getMovieByRating(double rating);
    List<Movie> getMovieByRatingGreaterThan(double rating);

}
