package mymedia.data;

import mymedia.models.AppUserMovie;
import mymedia.models.Movie;
import mymedia.security.AppUser;
import mymedia.security.AppUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AppUserMovieRepositoryTest {
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    AppUserMovieRepository userMovieRepository;
    @Autowired
    AppUserRepository userRepository;
    @Autowired
    MovieRepository movieRepository;

    private boolean isSetUp = false;

    @BeforeEach
    public void setUp() {
        if (!isSetUp) {
            KnownGoodState.setKnownGoodState(jdbcTemplate);
            isSetUp = true;
        }
    }

    @Test
    void shouldGetAtLeast2MoviesForUserJohnsmith() {
        Page<AppUserMovie> appUserMovies = userMovieRepository.findByUserUsername(
                Pageable.ofSize(10),
                "johnsmith"
        );
        assertTrue(2 <= appUserMovies.getNumberOfElements());
    }

    @Test
    void shouldGet1MovieForJohnsmithWithUserData() {
        Page<AppUserMovie> appUserMovies = userMovieRepository.findByUserUsername(
                Pageable.ofSize(1),
                "johnsmith"
        );
        List<AppUserMovie> appUserMoviesContent = appUserMovies.getContent();
        assertEquals(1, appUserMoviesContent.size());
        AppUserMovie appUserMovie = appUserMoviesContent.get(0);
        assertFalse(appUserMovie.isWatched());
    }

    @Test
    void shouldCreateEntryForJanedoe() {
        AppUser user = userRepository.findByUsername("janedoe");
        Movie movie = movieRepository.findById(1).orElse(null);
        assertNotNull(user);
        assertNotNull(movie);
        AppUserMovie userMovie = new AppUserMovie();
        userMovie.setUser(user);
        userMovie.setMovie(movie);
        AppUserMovie savedUserMovie = userMovieRepository.save(userMovie);
        assertEquals(4, savedUserMovie.getAppUserMovieId());
    }

    @Test
    void shouldUpdateEntryForJohnsmith() {
        AppUserMovie userMovie = userMovieRepository.findByUserUsername(
                Pageable.ofSize(1), "johnsmith").getContent().get(0);
        userMovie.setWatched(true);
        AppUserMovie savedMovie = userMovieRepository.save(userMovie);
        assertTrue(savedMovie.isWatched());
    }

}