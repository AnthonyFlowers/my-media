package mymedia.domain;

import mymedia.data.TvShowRepository;
import mymedia.models.Movie;
import mymedia.models.TvShow;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class TvShowService {
    private final TvShowRepository repository;

    public TvShowService(TvShowRepository repository) {
        this.repository = repository;
    }

    public Page<TvShow> findTvShows(int page, int pageSize) {
        return repository.findAll(PageRequest.of(
                page, pageSize,
                Sort.by(Sort.Direction.DESC, "releaseYear"))
        );
    }

    public TvShow findById(int tvShowId) {
        return repository.findById(tvShowId).orElse(null);
    }
}
