package mymedia.domain;

import mymedia.data.AppUserTvShowRepository;
import org.springframework.stereotype.Service;

@Service
public class TvShowService {
    private final AppUserTvShowRepository repository;

    public TvShowService(AppUserTvShowRepository repository) {
        this.repository = repository;
    }
}
