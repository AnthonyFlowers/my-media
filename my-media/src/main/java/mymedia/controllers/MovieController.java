package mymedia.controllers;

import mymedia.domain.MovieService;
import mymedia.domain.Result;
import mymedia.models.Movie;
import mymedia.security.AppUser;
import mymedia.security.AppUserService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

@Controller
@RequestMapping("/api/movie")
public class MovieController {
    private final MovieService movieService;
    private final AppUserService userService;

    public MovieController(MovieService movieService, AppUserService userService) {
        this.movieService = movieService;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<?> getMovies() {
        Result<Page<Movie>> result = movieService.findMoviesPaged(0);
        if(result.isSuccess()) {
            return new ResponseEntity<>(result.getPayload(), HttpStatus.OK);
        }
        return ErrorResponse.build(result);
    }

    @GetMapping("/user")
    public ResponseEntity<?> getUserMovies(@AuthenticationPrincipal AppUser appUser) {
        List<Movie> movies = movieService.findUserMovies(appUser);
        return new ResponseEntity<>(movies, HttpStatus.OK);
    }

    @GetMapping("/recent/{page}")
    public ResponseEntity<?> getRecentMovies(@PathVariable("page") int page) {
        Result<Page<Movie>> result = movieService.findMoviesPaged(page);
        if(result.isSuccess()){
            return new ResponseEntity<>(result.getPayload(), HttpStatus.OK);
        }
        return ErrorResponse.build(result);
    }

    @Transactional
    @PostMapping("/add")
    public ResponseEntity<?> addMovieToUser(@AuthenticationPrincipal AppUser appUser, @RequestBody Movie movie) {
        movie = movieService.findById(movie.getMovieId());
        movie.addUser(appUser);
        movieService.update(movie);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
