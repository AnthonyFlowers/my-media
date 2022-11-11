package mymedia.data;

import mymedia.models.Movie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class MovieRepositoryTest {

    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    MovieRepository repository;

    private boolean isSetUp = false;

    @BeforeEach
    public void setUp() {
        if (!isSetUp) {
            KnownGoodState.setKnownGoodState(jdbcTemplate);
            isSetUp = true;
        }
    }

    @Test
    void shouldGetAtLeast2Movies() throws Exception {
        assertTrue(2 <= repository.findAll().size());
    }

    @Test
    void shouldCreateNewMovie() {
        Movie spiderMan = new Movie();
        spiderMan.setMovieName("Spider-Man");
        spiderMan.setMovieLength(121);
        Movie actual = repository.save(spiderMan);
        assertEquals(4, actual.getMovieId());
        assertEquals("Spider-Man", actual.getMovieName());
    }

    @Test
    void shouldGetIronMan() {
        Movie movie = repository.findById(1).orElse(null);
        assertNotNull(movie);
        assertEquals("Iron Man", movie.getMovieName());
    }

    @Test
    void shouldUpdateIronMan2() {
        Movie ironMan2 = repository.findById(2).orElse(null);
        assertNotNull(ironMan2);
        ironMan2.setMovieLength(120);
        repository.save(ironMan2);

        Movie saved = repository.findById(2).orElse(null);
        assertNotNull(saved);
        assertEquals(120, saved.getMovieLength());
    }

    @Test
    void shouldDeleteIronMan3() {
        Movie ironMan3 = repository.findById(3).orElse(null);
        assertNotNull(ironMan3);
        repository.delete(ironMan3);
        Movie rm = repository.findById(3).orElse(null);
        assertNull(rm);
    }
}