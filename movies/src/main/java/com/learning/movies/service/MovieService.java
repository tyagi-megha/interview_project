package com.learning.movies.service;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import com.learning.movies.model.Movie;
import org.springframework.stereotype.Service;
import com.learning.movies.repository.MovieRepository;
import com.learning.movies.request.AddMovieRequest;
import com.learning.movies.request.UpdateMovieRequest;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieService implements IMovieService{

    private final MovieRepository movieRepository;

    @Override
    public Movie addMovies(AddMovieRequest request) {
        if(movieExists(request.getTitle(), request.getDirector())){
            throw new EntityExistsException("Movie with title " + request.getTitle() + " and director " +
                    request.getDirector() + "  exists");
        }

        return movieRepository.save(createmovie(request));
    }

    private boolean movieExists(String title, String director){
        return movieRepository.existsByTitleAndDirector(title, director);
    }

    private Movie createmovie(AddMovieRequest request)  {
        return new Movie(request.getTitle(),
                request.getDirector(),
                request.getRating()
                );
    }

    @Override
    public Movie updateMovies(UpdateMovieRequest request, Long movieId) {
        return movieRepository.findById(movieId)
                .map(existingMovie -> updateExistingMovie(existingMovie, request))
                .map(movieRepository ::  save)
                .orElseThrow(() -> new EntityNotFoundException("Movie with ID " + movieId + "  does not exist"));
    }

    private Movie updateExistingMovie(Movie existingMovie, UpdateMovieRequest request) {
        existingMovie.setTitle(request.getTitle());
        existingMovie.setDirector(request.getDirector());
        existingMovie.setRating(request.getRating());

        return existingMovie;
    }

    @Override
    public Movie getMovieById(Long movieId) {
        return movieRepository.findById(movieId).orElseThrow(
                () ->  new EntityNotFoundException("Movie not found")
        );
    }

    @Override
    public void deleteMovieById(Long movieId) {
        if(!movieRepository.existsById(movieId)){
            throw new EntityNotFoundException("Movie does not exists");
        }
        movieRepository.deleteById(movieId);
    }

    @Override
    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    @Override
    public List<Movie> getMovieByTitle(String title) {
        return movieRepository.findByTitle(title);
    }

    @Override
    public List<Movie> getMovieByDirector(String director) {
        return movieRepository.findByDirector(director);
    }

    @Override
    public List<Movie> getMovieByRating(double rating) {
        return movieRepository.findByRating(rating);
    }

    @Override
    public List<Movie> getMovieByRatingGreaterThan(double rating) {
        return movieRepository.findByRatingGreaterThan(rating);
    }
}
