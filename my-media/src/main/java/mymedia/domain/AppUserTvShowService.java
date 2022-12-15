package mymedia.domain;

import mymedia.data.AppUserTvShowRepository;
import mymedia.models.AppUserTvShow;
import mymedia.models.TvShow;
import mymedia.security.AppUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class AppUserTvShowService {

    private final AppUserTvShowRepository userTvShowRepository;
    private final int defaultSmallPageSize = 10;

    public AppUserTvShowService(AppUserTvShowRepository userTvShowRepository) {
        this.userTvShowRepository = userTvShowRepository;
    }


    public Page<AppUserTvShow> findUserMovies(int page, int pageSize, AppUser appUser) {
        return userTvShowRepository.findByUserUsername(PageRequest.of(
                Math.max(page, 0), pageSize < 0 ? defaultSmallPageSize : pageSize),
                appUser.getUsername()
        );
    }

    public Result<AppUserTvShow> create(TvShow tvShow, AppUser appUser) {
        Result<AppUserTvShow> result = new Result<>();
        if(tvShow == null){
            result.addMessage(ResultType.INVALID, "cannot add an entry with a null TV Show");
            return result;
        }
        AppUserTvShow found = userTvShowRepository.findByUserAppUserIdAndTvShowTvShowId(
                appUser.getAppUserId(),
                tvShow.getTvShowId()
        );
        if(found != null) {
            result.addMessage(ResultType.INVALID, "user already has an entry for that TV Show");
        } else {
            AppUserTvShow appUserTvShow = new AppUserTvShow(tvShow, appUser);
            result.setPayload(userTvShowRepository.save(appUserTvShow));
        }
        return result;
    }
}
