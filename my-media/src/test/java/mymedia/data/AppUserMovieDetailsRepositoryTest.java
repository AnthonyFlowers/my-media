package mymedia.data;

import mymedia.models.AppUserMovieDetails;
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
class AppUserMovieDetailsRepositoryTest {
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    AppUserMovieRepository repository;

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
        Page<AppUserMovieDetails> appUserMovies = repository.findByAppUserUsername(
                Pageable.ofSize(10),
                "johnsmith"
        );
        assertTrue(2 <= appUserMovies.getNumberOfElements());
    }

    @Test
    void shouldGet1MovieForJohnsmithWithUserData() {
        Page<AppUserMovieDetails> appUserMovies = repository.findByAppUserUsername(
                Pageable.ofSize(1),
                "johnsmith"
        );
        List<AppUserMovieDetails> appUserMoviesContent = appUserMovies.getContent();
        assertEquals(1, appUserMoviesContent.size());
        AppUserMovieDetails appUserMovieDetails = appUserMoviesContent.get(0);
        assertFalse(appUserMovieDetails.isWatched());
    }

}