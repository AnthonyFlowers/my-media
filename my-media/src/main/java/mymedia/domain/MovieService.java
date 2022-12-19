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
    private final PageRequest smallPr;

    public MovieService(MovieRepository movieRepository, Validator validator) {
        this.movieRepository = movieRepository;
        this.validator = validator;
        this.smallPr = PageRequest.of(0, 10);
        ;
    }

    public Page<Movie> findMovies(int page, int pageSize) {
        return movieRepository.findAll(PageRequest.of(
                Math.max(page, 0), pageSize < 0 ? defaultSmallPageSize : pageSize,
                Sort.by(Sort.Direction.DESC, "movieYear")
        ));
    }

    public Page<Movie> findRecentMovies(Integer page, Integer pageSize) {
        if (page == null || page < 0) {
            page = 0;
        }
        if (pageSize == null || page <= 0) {
            pageSize = defaultSmallPageSize;
        }
        return movieRepository.findAll(PageRequest.of(
                page,
                pageSize,
                Sort.by(Sort.Direction.DESC, "movieYear"))
        );
    }

    public void update(Movie movie) {
        movieRepository.save(movie);
    }

    public Movie findById(int movieId) {
        return movieRepository.findById(movieId).orElse(null);
    }

    public Page<Movie> search(String nameQuery) {
        return movieRepository.findByMovieNameContainsIgnoreCase(smallPr, nameQuery);
    }
}
