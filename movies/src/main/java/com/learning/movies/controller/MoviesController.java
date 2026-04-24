package com.learning.movies.controller;

import lombok.RequiredArgsConstructor;
import com.learning.movies.model.Movie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.learning.movies.request.AddMovieRequest;
import com.learning.movies.request.UpdateMovieRequest;
import com.learning.movies.response.ApiResponse;
import com.learning.movies.service.IMovieService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/movies")
public class MoviesController {

    private final IMovieService movieService;

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addMovie(@RequestBody AddMovieRequest request){
        Movie movie = movieService.addMovies(request);
        return ResponseEntity.ok(new ApiResponse("Movie added successfully", movie));
    }

    @PutMapping("/movie/{movieId}/update")
    public ResponseEntity<ApiResponse> updateMovie(@RequestBody UpdateMovieRequest request,
                                                   @PathVariable Long movieId){
        return ResponseEntity.ok(new ApiResponse("Movie updated successfully", movieService.updateMovies(request, movieId)));
    }


    @DeleteMapping("/movie/{movieId}/delete")
    public ResponseEntity<ApiResponse> deleteMovie(@PathVariable Long movieId){
        movieService.deleteMovieById(movieId);
        return ResponseEntity.ok(new ApiResponse("Movie with id " + movieId + " deleted successfully", null));
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllMovies(){
        List<Movie> movieList = movieService.getAllMovies();
        return ResponseEntity.ok(new ApiResponse("Movies", movieList));
    }

    @GetMapping("/movie/movieByTitle")
    public ResponseEntity<ApiResponse> getMovieByTitle(@RequestParam String title){
        List<Movie> movieList = movieService.getMovieByTitle(title);
        return ResponseEntity.ok(new ApiResponse("Movies with title "+ title, movieList));
    }

    @GetMapping("/movie/movieByDirector")
    public ResponseEntity<ApiResponse> getMovieByDirector(@RequestParam String director){
        List<Movie> movieList = movieService.getMovieByDirector(director);
        return ResponseEntity.ok(new ApiResponse("Movies with director "+ director, movieList));
    }

    @GetMapping("/movie/movieByRating")
    public ResponseEntity<ApiResponse> getMovieByRating(@RequestParam double rating){
        List<Movie> movieList = movieService.getMovieByRating(rating);
        return ResponseEntity.ok(new ApiResponse("Movies with rating "+ rating, movieList));
    }

    @GetMapping("/movie/movieByGreaterRating")
    public ResponseEntity<ApiResponse> getMovieByGreaterRating(@RequestParam double rating){
        List<Movie> movieList = movieService.getMovieByRatingGreaterThan(rating);
        return ResponseEntity.ok(new ApiResponse("Movies with rating greater than "+ rating, movieList));
    }

    @GetMapping("/movie/{movieId}/movieById")
    public Movie getMovieById(@PathVariable Long movieId){
        return movieService.getMovieById(movieId);
    }
}
