package mymedia.domain;

import mymedia.data.MovieRepository;
import mymedia.models.Movie;
import mymedia.security.AppUser;
import mymedia.security.AppUserRepository;
import mymedia.security.AppUserService;
import org.springframework.stereotype.Service;

import javax.validation.Validator;
import java.util.List;

@Service
public class MovieService {
    private final MovieRepository movieRepository;
    private final AppUserService userService;
    private final Validator validator;


    public MovieService(MovieRepository movieRepository, AppUserService userService, Validator validator) {
        this.movieRepository = movieRepository;
        this.userService = userService;
        this.validator = validator;
    }

    public List<Movie> findAllMovies() {
        return movieRepository.findAll();
    }

    public List<Movie> findUserMovies(AppUser user) {
        if(user == null) return findAllMovies();
        return movieRepository.findByUsers(user);
    }

    public Movie findById(int movieId) {
        return movieRepository.findById(movieId).orElse(null);
    }

    public void update(Movie movie) {
        movieRepository.save(movie);
    }
}
