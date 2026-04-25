package com.movies.reviewsservice.integration;

import com.movies.reviewsservice.dto.MovieDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "movie-service", url = "http://localhost:9092") // adjust port
public interface MovieServiceClient {

    @GetMapping("${api.prefix}/movies/movie/{movieId}/movieById")
    MovieDto getMovieById(@PathVariable Long movieId);

}
