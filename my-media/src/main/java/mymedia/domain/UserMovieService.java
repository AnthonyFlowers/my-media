package mymedia.domain;

import mymedia.data.AppUserMovieRepository;
import mymedia.models.AppUserMovie;
import mymedia.models.Movie;
import mymedia.security.AppUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class UserMovieService {

    private final AppUserMovieRepository appUserMovieRepository;

    public UserMovieService(AppUserMovieRepository appUserMovieRepository) {
        this.appUserMovieRepository = appUserMovieRepository;
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

    public AppUserMovie createUserEntry(AppUser user, Movie movie) {
        AppUserMovie appUserMovie = new AppUserMovie();
        appUserMovie.setMovie(movie);
        appUserMovie.setUser(user);
        return appUserMovieRepository.save(appUserMovie);
    }

    public void updateUserMovie(AppUserMovie userMovie, AppUser appUser) {
        userMovie.setUser(appUser);
        appUserMovieRepository.save(userMovie);
    }
}
