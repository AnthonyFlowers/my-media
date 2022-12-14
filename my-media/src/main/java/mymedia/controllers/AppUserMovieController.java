package mymedia.controllers;

import mymedia.domain.AppUserMovieService;
import mymedia.domain.Result;
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

public class AppUserMovieController {

    private final AppUserMovieService appUserMovieService;

    public AppUserMovieController(AppUserMovieService appUserMovieService) {
        this.appUserMovieService = appUserMovieService;
    }

    @GetMapping
    public ResponseEntity<?> getUserMovies(
            @AuthenticationPrincipal AppUser appUser,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int pageSize) {
        Page<AppUserMovie> movies = appUserMovieService.findUserMovies(page, pageSize, appUser);
        return new ResponseEntity<>(movies, HttpStatus.OK);
    }

    @GetMapping("/{appUserMovieId}")
    public ResponseEntity<?> getUserMovie(
            @AuthenticationPrincipal AppUser appUser,
            @PathVariable int appUserMovieId) {
        AppUserMovie movie = appUserMovieService.findByUserMovieIdAndUser(appUserMovieId, appUser);
        return new ResponseEntity<>(movie, HttpStatus.OK);
    }

    @Transactional
    @PostMapping
    public ResponseEntity<?> createUserMovieEntry(
            @AuthenticationPrincipal AppUser appUser,
            @RequestBody Movie movie) {
        AppUserMovie userMovie = appUserMovieService.saveAppUserMovie(appUser, movie);
        return new ResponseEntity<>(userMovie, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<?> updateUserMovieEntry(
            @AuthenticationPrincipal AppUser appUser,
            @RequestBody AppUserMovie userMovie) {
        Result<AppUserMovie> result = appUserMovieService.updateAppUserMovie(userMovie, appUser);
        if (result.isSuccess()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ErrorResponse.build(result);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUserMovieEntry(
            @AuthenticationPrincipal AppUser appUser,
            @RequestBody AppUserMovie userMovie) {
        if (appUserMovieService.deleteAppUserMovie(userMovie, appUser)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
