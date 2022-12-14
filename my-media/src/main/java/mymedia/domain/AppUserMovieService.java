package mymedia.domain;

import mymedia.data.AppUserMovieRepository;
import mymedia.models.AppUserMovie;
import mymedia.models.Movie;
import mymedia.security.AppUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class AppUserMovieService {

    private final AppUserMovieRepository appUserMovieRepository;

    public AppUserMovieService(AppUserMovieRepository appUserMovieRepository) {
        this.appUserMovieRepository = appUserMovieRepository;
    }

    public AppUserMovie findByUserMovieIdAndUser(int userMovieId, AppUser user) {
        return appUserMovieRepository.findByAppUserMovieIdAndUser(userMovieId, user);
    }

    public AppUserMovie findByUserMovieId(int userMovieId) {
        return appUserMovieRepository.findById(userMovieId).orElse(null);
    }

    public Page<AppUserMovie> findUserMovies(int page, int pageSize, AppUser user) {
        return appUserMovieRepository.findByUserUsername(
                PageRequest.of(
                        Math.max(page, 0),
                        pageSize <= 0 ? 10 : pageSize
                ),
                user.getUsername()
        );
    }

    public Result<AppUserMovie> saveAppUserMovie(AppUser user, Movie movie) {
        Result<AppUserMovie> result = new Result<>();
        if(movie == null) {
            result.addMessage(ResultType.INVALID, "cannot create entry without movie");
            return result;
        }
        AppUserMovie foundUserMovie = appUserMovieRepository.findByUserAppUserIdAndMovieMovieId(
                user.getAppUserId(),
                movie.getMovieId()
        );
        if (foundUserMovie != null) {
            result.addMessage(ResultType.INVALID, "That user already has an entry for that movie");
            return result;
        }
        AppUserMovie appUserMovie = new AppUserMovie();
        appUserMovie.setMovie(movie);
        appUserMovie.setUser(user);
        result.setPayload(appUserMovieRepository.save(appUserMovie));
        return result;
    }

    public Result<AppUserMovie> updateAppUserMovie(AppUserMovie userMovie, AppUser appUser) {
        Result<AppUserMovie> result = new Result<>();
        AppUserMovie foundAppUserMovie = findByUserMovieId(userMovie.getAppUserMovieId());
        if (foundAppUserMovie == null) {
            result.addMessage(ResultType.NOT_FOUND, "Could not find that movie entry to update");
        } else if (foundAppUserMovie.getUserId() != appUser.getAppUserId()) {
            result.addMessage(ResultType.INVALID, "You do not own that movie entry");
        } else {
            userMovie.setMovie(foundAppUserMovie.getMovie());
            userMovie.setUser(appUser);
            result.setPayload(appUserMovieRepository.save(userMovie));
        }
        return result;
    }

    public Result<?> deleteAppUserMovie(AppUserMovie userMovie, AppUser appUser) {
        Result<?> result = new Result<>();
        AppUserMovie foundAppUserMovie = findByUserMovieId(userMovie.getAppUserMovieId());
        if (foundAppUserMovie == null) {
            result.addMessage(ResultType.NOT_FOUND, "Could not find that user movie entry");
            return result;
        }
        if (foundAppUserMovie.getUserId() != appUser.getAppUserId()) {
            result.addMessage(ResultType.INVALID, "Could not update that user movie entry");
        }
        appUserMovieRepository.delete(foundAppUserMovie);
        return result;
    }
}
