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
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/api/movie")

public class UserMovieController {

    private final UserMovieService movieService;

    public UserMovieController(UserMovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/user")
    public ResponseEntity<?> getUserMovies(
            @AuthenticationPrincipal AppUser appUser,
            @RequestParam Optional<Integer> page,
            @RequestParam Optional<Integer> pageSize) {
        Page<AppUserMovie> movies = movieService.findUserMovies(page.orElse(0), pageSize.orElse(10), appUser);
        return new ResponseEntity<>(movies, HttpStatus.OK);
    }

    @Transactional
    @PostMapping("/add")
    public ResponseEntity<?> createUserMovieEntry(@AuthenticationPrincipal AppUser appUser, @RequestBody Movie movie) {
        AppUserMovie userMovie = movieService.createUserEntry(appUser, movie);
        return new ResponseEntity<>(userMovie, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateUserMovieEntry(
            @AuthenticationPrincipal AppUser appUser, @RequestBody AppUserMovie userMovie) {
        movieService.updateUserMovie(userMovie, appUser);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
