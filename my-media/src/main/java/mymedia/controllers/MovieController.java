package mymedia.controllers;

import mymedia.domain.MovieService;
import mymedia.models.Movie;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/api/movie")
public class MovieController {
    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping
    public ResponseEntity<?> getMovies(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "50") int pageSize) {
        Page<Movie> movies = movieService.findMovies(page, pageSize);
        return new ResponseEntity<>(movies, HttpStatus.OK);
    }

    @GetMapping("/recent")
    public ResponseEntity<?> getRecentMovies(
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        Page<Movie> movies = movieService.findRecentMovies(page, pageSize);
        return new ResponseEntity<>(movies, HttpStatus.OK);
    }
}
