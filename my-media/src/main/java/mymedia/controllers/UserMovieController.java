package mymedia.controllers;

import mymedia.domain.Result;
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

    private final UserMovieService userMovieService;

    public UserMovieController(UserMovieService userMovieService) {
        this.userMovieService = userMovieService;
    }

    @GetMapping
    public ResponseEntity<?> getUserMovies(
            @AuthenticationPrincipal AppUser appUser,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int pageSize) {
        Page<AppUserMovie> movies = userMovieService.findUserMovies(page, pageSize, appUser);
        return new ResponseEntity<>(movies, HttpStatus.OK);
    }

    @GetMapping("/{appUserMovieId}")
    public ResponseEntity<?> getUserMovie(
            @AuthenticationPrincipal AppUser appUser,
            @PathVariable int appUserMovieId) {
        AppUserMovie movie = userMovieService.findByUserMovieIdAndUser(appUserMovieId, appUser);
        return new ResponseEntity<>(movie, HttpStatus.OK);
    }

    @Transactional
    @PostMapping
    public ResponseEntity<?> createUserMovieEntry(
            @AuthenticationPrincipal AppUser appUser,
            @RequestBody Movie movie) {
        AppUserMovie userMovie = userMovieService.createUserEntry(appUser, movie);
        return new ResponseEntity<>(userMovie, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<?> updateUserMovieEntry(
            @AuthenticationPrincipal AppUser appUser,
            @RequestBody AppUserMovie userMovie) {
        Result<AppUserMovie> result = userMovieService.updateUserMovie(userMovie, appUser);
        if (result.isSuccess()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ErrorResponse.build(result);
    }
}
