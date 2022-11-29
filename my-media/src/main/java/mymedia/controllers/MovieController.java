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
import java.util.Map;

@Controller
@RequestMapping("/api/movie")
public class MovieController {
    private final MovieService movieService;
    private final AppUserService userService;

    public MovieController(MovieService movieService, AppUserService userService) {
        this.movieService = movieService;
        this.userService = userService;
    }

    @GetMapping("/{page}")
    public ResponseEntity<?> getMovies(@PathVariable(required = false) Integer page) {
        if(page == null){
            page = 0;
        }
        Result<Page<Movie>> result = movieService.findMoviesPaged(page);
        if (result.isSuccess()) {
            return new ResponseEntity<>(result.getPayload(), HttpStatus.OK);
        }
        return ErrorResponse.build(result);
    }

    @GetMapping
    public ResponseEntity<?> getMovies(@RequestParam Map<String, String> query) {
        Result<Page<Movie>> result = movieService.findMoviesPaged(query);
        if (result.isSuccess()) {
            return new ResponseEntity<>(result.getPayload(), HttpStatus.OK);
        }
        return ErrorResponse.build(result);
    }

    @GetMapping("/user/{page}")
    public ResponseEntity<?> getUserMovies(
            @AuthenticationPrincipal AppUser appUser,
            @PathVariable(required = false) Integer page) {
        page = page == null ? 0: page;
        Page<Movie> movies = movieService.findUserMovies(page, appUser);
        return new ResponseEntity<>(movies, HttpStatus.OK);
    }

    @GetMapping("/recent/{page}")
    public ResponseEntity<?> getRecentMovies(@PathVariable int page) {
        Result<Page<Movie>> result = movieService.findMoviesPaged(page);
        if (result.isSuccess()) {
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
