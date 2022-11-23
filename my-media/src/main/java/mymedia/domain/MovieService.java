package mymedia.domain;

import mymedia.data.MovieRepository;
import mymedia.models.Movie;
import mymedia.security.AppUser;
import mymedia.security.AppUserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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

    public Result<Page<Movie>> findMoviesPaged(int pageNumber) {
        Result<Page<Movie>> result = new Result<>();
        if (pageNumber < 0) {
            result.addMessage(ResultType.INVALID, "movie page index can not be negative");
        }
        if (result.isSuccess()) {
            result.setPayload(movieRepository.findAll(PageRequest.of(
                    pageNumber, 100,
                    Sort.by(Sort.Direction.DESC, "movieYear")
            )));
        }
        return result;
    }

    public Page<Movie> findAllMovies() {
        return movieRepository.findAll(PageRequest.of(0, 100, Sort.by(Sort.Direction.DESC, "movieYear")));
    }

    public List<Movie> findUserMovies(AppUser user) {
        return movieRepository.findByUsers(user);
    }

    public Movie findById(int movieId) {
        return movieRepository.findById(movieId).orElse(null);
    }

    public List<Movie> findRecentMovies() {
        return movieRepository.findAll(PageRequest.of(0, 100, Sort.by(Sort.Direction.DESC, "movieYear")))
                .stream()
                .toList();
    }

    public void update(Movie movie) {
        movieRepository.save(movie);
    }
}
