package mymedia.domain;

import mymedia.data.AppUserTvShowRepository;
import mymedia.data.TvShowRepository;
import org.springframework.stereotype.Service;

@Service
public class TvShowService {
    private final TvShowRepository repository;

    public TvShowService(TvShowRepository repository) {
        this.repository = repository;
    }
}
