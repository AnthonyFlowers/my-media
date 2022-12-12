package mymedia.controllers;

import mymedia.domain.UserMovieService;
import mymedia.models.AppUserMovie;
import mymedia.models.Movie;
import mymedia.security.AppUser;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;

@Controller
@RequestMapping("/api/user/movie")

public class UserMovieController {

    private final UserMovieService movieService;

    public UserMovieController(UserMovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping
    public ResponseEntity<?> getUserMovies(
            @AuthenticationPrincipal AppUser appUser,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int pageSize) {
        Page<AppUserMovie> movies = movieService.findUserMovies(page, pageSize, appUser);
        return new ResponseEntity<>(movies, HttpStatus.OK);
    }

    @Transactional
    @PostMapping
    public ResponseEntity<?> createUserMovieEntry(
            @AuthenticationPrincipal AppUser appUser,
            @RequestBody Movie movie) {
        AppUserMovie userMovie = movieService.createUserEntry(appUser, movie);
        return new ResponseEntity<>(userMovie, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<?> updateUserMovieEntry(
            @AuthenticationPrincipal AppUser appUser,
            @RequestBody AppUserMovie userMovie) {
        movieService.updateUserMovie(userMovie, appUser);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
