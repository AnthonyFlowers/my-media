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

    private final AppUserMovieRepository repository;

    public AppUserMovieService(AppUserMovieRepository repository) {
        this.repository = repository;
    }

    public AppUserMovie findByUserMovieIdAndUser(int userMovieId, AppUser user) {
        return repository.findByAppUserMovieIdAndUser(userMovieId, user);
    }

    public AppUserMovie findByUserMovieId(int userMovieId) {
        return repository.findById(userMovieId).orElse(null);
    }

    public Page<AppUserMovie> findUserMovies(int page, int pageSize, AppUser user) {
        return repository.findByUserUsername(
                PageRequest.of(
                        Math.max(page, 0),
                        pageSize <= 0 ? 10 : pageSize
                ),
                user.getUsername()
        );
    }

    public Result<AppUserMovie> create(AppUser user, Movie movie) {
        Result<AppUserMovie> result = new Result<>();
        if (movie == null) {
            result.addMessage(ResultType.INVALID, "cannot create entry without movie");
            return result;
        }
        AppUserMovie foundUserMovie = repository.findByUserAppUserIdAndMovieMovieId(
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
        result.setPayload(repository.save(appUserMovie));
        return result;
    }

    public Result<AppUserMovie> update(AppUserMovie userMovie, AppUser appUser) {
        Result<AppUserMovie> result = new Result<>();
        AppUserMovie foundAppUserMovie = findByUserMovieId(userMovie.getAppUserMovieId());
        if (foundAppUserMovie == null) {
            result.addMessage(ResultType.NOT_FOUND, "Could not find that movie entry to update");
        } else if (foundAppUserMovie.getUserId() != appUser.getAppUserId()) {
            result.addMessage(ResultType.INVALID, "You do not own that movie entry");
        } else {
            userMovie.setMovie(foundAppUserMovie.getMovie());
            userMovie.setUser(appUser);
            result.setPayload(repository.save(userMovie));
        }
        return result;
    }

    public Result<?> delete(AppUserMovie userMovie, AppUser appUser) {
        Result<?> result = new Result<>();
        AppUserMovie foundAppUserMovie = findByUserMovieId(userMovie.getAppUserMovieId());
        if (foundAppUserMovie == null) {
            result.addMessage(ResultType.NOT_FOUND, "Could not find that user movie entry");
            return result;
        }
        if (foundAppUserMovie.getUserId() != appUser.getAppUserId()) {
            result.addMessage(ResultType.INVALID, "Could not update that user movie entry");
        } else {
            repository.delete(foundAppUserMovie);
        }
        return result;
    }
}
