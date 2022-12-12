package mymedia.domain;

import mymedia.data.AppUserTvShowRepository;
import org.springframework.stereotype.Service;

@Service
public class UserTvShowService {

    private final AppUserTvShowRepository userTvShowRepository;

    public UserTvShowService(AppUserTvShowRepository userTvShowRepository) {
        this.userTvShowRepository = userTvShowRepository;
    }
}
