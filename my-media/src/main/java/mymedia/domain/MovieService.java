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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public Result<Page<Movie>> findMoviesPaged(int pageNumber){
        return findMoviesPaged(pageNumber, 100);
    }

    public Result<Page<Movie>> findMoviesPaged(int pageNumber, int perPage) {
        Result<Page<Movie>> result = new Result<>();
        if (pageNumber < 0) {
            result.addMessage(ResultType.INVALID, "movie page index can not be negative");
        }
        if (result.isSuccess()) {
            result.setPayload(movieRepository.findAll(PageRequest.of(
                    pageNumber, perPage,
                    Sort.by(Sort.Direction.DESC, "movieYear")
            )));
        }
        return result;
    }

    public Page<Movie> findAllMovies() {
        return movieRepository.findAll(PageRequest.of(0, 100, Sort.by(Sort.Direction.DESC, "movieYear")));
    }

    public Page<Movie> findUserMovies(Map<String, String> query, AppUser user) {
        Map<MovieQueryParam, Integer> parsedQuery = parseMovieParams(query);
        int page = parsedQuery.getOrDefault(MovieQueryParam.PAGE, 0);
        int pageSize = parsedQuery.getOrDefault(MovieQueryParam.PAGE_SIZE, 100);
        return movieRepository.findByUsers(PageRequest.of(page, pageSize), user);
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

    public Result<Page<Movie>> findMoviesPaged(Map<String, String> query) {
        Result<Page<Movie>> result = new Result<>();
        Map<MovieQueryParam, Integer> parsedQuery = parseMovieParams(query);
        int page = parsedQuery.getOrDefault(MovieQueryParam.PAGE, 0);
        int pageSize = parsedQuery.getOrDefault(MovieQueryParam.PAGE_SIZE, 10);
        return findMoviesPaged(page, pageSize);
    }

    private Map<MovieQueryParam, Integer> parseMovieParams(Map<String, String> query) {
        Map<MovieQueryParam, Integer> parsedQuery = new HashMap<>();
        for (MovieQueryParam param : MovieQueryParam.values()) {
            if(query.containsKey(param.getUrlParam())){
                parsedQuery.put(param, Integer.parseInt(query.get(param.getUrlParam())));
            }
        }
        return parsedQuery;
    }
}
