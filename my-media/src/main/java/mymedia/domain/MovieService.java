package mymedia.domain;

import mymedia.data.MovieRepository;
import mymedia.models.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.validation.Validator;

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

    public Result<Page<Movie>> findMovies(int pageNumber) {
        return findMovies(pageNumber, defaultPageSize);
    }

    public Result<Page<Movie>> findMovies(int pageNumber, int pageSize) {
        Result<Page<Movie>> result = new Result<>();
        if (pageNumber < 0) {
            result.addMessage(ResultType.INVALID, "movie page index can not be negative");
        }
        if (result.isSuccess()) {
            result.setPayload(movieRepository.findAll(PageRequest.of(
                    pageNumber, pageSize,
                    Sort.by(Sort.Direction.DESC, "movieYear")
            )));
        }
        return result;
    }

    public Page<Movie> findAllMovies() {
        return findMovies(0, defaultPageSize).getPayload();
    }

    public Page<Movie> findRecentMovies() {
        return movieRepository.findAll(PageRequest.of(0,
                defaultSmallPageSize, Sort.by(Sort.Direction.DESC, "movieYear"))
        );
    }

    public void update(Movie movie) {
        movieRepository.save(movie);
    }
}
