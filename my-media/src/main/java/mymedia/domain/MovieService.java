package mymedia.domain;

import mymedia.data.MovieRepository;
import mymedia.models.Movie;
import org.springframework.stereotype.Service;

import javax.validation.Validator;
import java.util.List;

@Service
public class MovieService {
    private final MovieRepository repository;
    private final Validator validator;


    public MovieService(MovieRepository repository, Validator validator) {
        this.repository = repository;
        this.validator = validator;
    }

    public List<Movie> findAllMovies() {
        return repository.findAll();
    }

}
