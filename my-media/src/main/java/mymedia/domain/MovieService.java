package mymedia.domain;

import mymedia.data.MovieRepository;
import mymedia.models.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.validation.Validator;
import java.util.Optional;

@Service
public class MovieService {
    private final MovieRepository movieRepository;
    private final Validator validator;
    private final int defaultPageSize = 50;
    private final int defaultSmallPageSize = 10;

    public MovieService(MovieRepository movieRepository, Validator validator) {
        this.movieRepository = movieRepository;
        this.validator = validator;
    }

    public Page<Movie> findMovies(int page, int pageSize) {
        return movieRepository.findAll(PageRequest.of(
                Math.max(page, 0), pageSize < 0 ? 50 : pageSize,
                Sort.by(Sort.Direction.DESC, "movieYear")
            ));
    }

    public Page<Movie> findAllMovies() {
        return findMovies(0, defaultPageSize);
    }

    public Page<Movie> findRecentMovies(int page, int pageSize) {
        return movieRepository.findAll(PageRequest.of(
                Math.max(page, 0),
                pageSize, Sort.by(Sort.Direction.DESC, "movieYear"))
        );
    }

    public Page<Movie> findRecentMovies(int page) {
        return findRecentMovies(page, defaultPageSize);
    }

    public void update(Movie movie) {
        movieRepository.save(movie);
    }
}
