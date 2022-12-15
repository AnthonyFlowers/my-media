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

    private final AppUserTvShowRepository repository;
    private final int defaultSmallPageSize = 10;

    public AppUserTvShowService(AppUserTvShowRepository repository) {
        this.repository = repository;
    }


    public Page<AppUserTvShow> findUserTvShows(int page, int pageSize, AppUser appUser) {
        return repository.findByUserUsername(PageRequest.of(
                        Math.max(page, 0), pageSize < 0 ? defaultSmallPageSize : pageSize),
                appUser.getUsername()
        );
    }

    public Result<AppUserTvShow> create(TvShow tvShow, AppUser appUser) {
        Result<AppUserTvShow> result = new Result<>();
        if (tvShow == null) {
            result.addMessage(ResultType.INVALID, "cannot add an entry with a null TV Show");
            return result;
        }
        AppUserTvShow found = repository.findByUserAppUserIdAndTvShowTvShowId(
                appUser.getAppUserId(),
                tvShow.getTvShowId()
        );
        if (found != null) {
            result.addMessage(ResultType.INVALID, "user already has an entry for that TV Show");
        } else {
            AppUserTvShow appUserTvShow = new AppUserTvShow(tvShow, appUser);
            result.setPayload(repository.save(appUserTvShow));
        }
        return result;
    }

    public Result<?> delete(AppUserTvShow userTvShow, AppUser appUser) {
        Result<?> result = new Result<>();
        AppUserTvShow foundAppUserTvShow = findByUserTvShowId(userTvShow.getAppUserTvShowId());
        if (foundAppUserTvShow == null) {
            result.addMessage(ResultType.NOT_FOUND, "Could not find that tv show entry");
            return result;
        }
        if (foundAppUserTvShow.getUserId() != appUser.getAppUserId()) {
            result.addMessage(ResultType.INVALID, "Could not update that tv show entry");
        } else {
            repository.delete(foundAppUserTvShow);
        }
        return result;
    }

    private AppUserTvShow findByUserTvShowId(int appUserTvShowId) {
        return repository.findById(appUserTvShowId).orElse(null);
    }

    public Result<AppUserTvShow> update(AppUserTvShow userTvShow, AppUser appUser) {
        Result<AppUserTvShow> result = new Result<>();
        AppUserTvShow foundAppUserTvShow = findByUserTvShowId(userTvShow.getAppUserTvShowId());
        if (foundAppUserTvShow == null) {
            result.addMessage(ResultType.NOT_FOUND, "Could not find that tv show entry to update");
        } else if (foundAppUserTvShow.getUserId() != appUser.getAppUserId()) {
            result.addMessage(ResultType.INVALID, "Could not update that tv show entry");
        } else {
            userTvShow.setTvShow(foundAppUserTvShow.getTvShow());
            userTvShow.setUser(appUser);
            result.setPayload(repository.save(userTvShow));
        }
        return result;
    }
}
