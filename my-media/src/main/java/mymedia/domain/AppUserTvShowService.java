package mymedia.domain;

import mymedia.data.AppUserTvShowRepository;
import org.springframework.stereotype.Service;

@Service
public class AppUserTvShowService {

    private final AppUserTvShowRepository userTvShowRepository;

    public AppUserTvShowService(AppUserTvShowRepository userTvShowRepository) {
        this.userTvShowRepository = userTvShowRepository;
    }
}
