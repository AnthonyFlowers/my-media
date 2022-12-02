package mymedia.domain;

import mymedia.data.AppUserMovieRepository;
import mymedia.data.MovieRepository;
import mymedia.models.AppUserMovie;
import mymedia.models.Movie;
import mymedia.security.AppUser;
import mymedia.security.AppUserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.validation.Validator;
import java.util.HashMap;
import java.util.Map;

@Service
public class MovieService {
    private final MovieRepository movieRepository;
    private final AppUserMovieRepository appUserMovieRepository;
    private final AppUserService userService;
    private final Validator validator;


    public MovieService(MovieRepository movieRepository, AppUserMovieRepository appUserMovieRepository,
                        AppUserService userService, Validator validator) {
        this.movieRepository = movieRepository;
        this.appUserMovieRepository = appUserMovieRepository;
        this.userService = userService;
        this.validator = validator;
    }

    public Result<Page<Movie>> findMovies(int pageNumber){
        return findMovies(pageNumber, 100);
    }

    public Result<Page<Movie>> findMovies(int pageNumber, int perPage) {
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
        return findMovies(0).getPayload();
    }

    public Page<AppUserMovie> findUserMovies(Map<String, String> query, AppUser user) {
        Map<MovieQueryParam, Integer> parsedQuery = parseMovieParams(query);
        int page = parsedQuery.getOrDefault(MovieQueryParam.PAGE, 0);
        int pageSize = parsedQuery.getOrDefault(MovieQueryParam.PAGE_SIZE, 100);
        return appUserMovieRepository.findMoviesByUser(PageRequest.of(page, pageSize), user);
    }

    public Movie findById(int movieId) {
        return movieRepository.findById(movieId).orElse(null);
    }

    public Page<Movie> findRecentMovies() {
        return movieRepository.findAll(PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "movieYear")));
    }

    public void update(Movie movie) {
        movieRepository.save(movie);
    }

    public Result<Page<Movie>> findMovies(Map<String, String> query) {
        Map<MovieQueryParam, Integer> parsedQuery = parseMovieParams(query);
        int page = parsedQuery.getOrDefault(MovieQueryParam.PAGE, 0);
        int pageSize = parsedQuery.getOrDefault(MovieQueryParam.PAGE_SIZE, 10);
        return findMovies(page, pageSize);
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

    public void createUserEntry(AppUser user, Movie movie) {
        AppUserMovie appUserMovie = new AppUserMovie();
        appUserMovie.setMovie(movie);
        appUserMovie.setUser(user);
        appUserMovieRepository.save(appUserMovie);
    }
}
