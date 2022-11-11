package mymedia.data;

import mymedia.models.TvShow;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class TvShowRepositoryTest {

    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    TvShowRepository repository;

    private boolean isSetUp = false;

    @BeforeEach
    public void setUp() {
        if (!isSetUp) {
            KnownGoodState.setKnownGoodState(jdbcTemplate);
            isSetUp = true;
        }
    }

    @Test
    void shouldFindAtLeast2TVShows() {
        assertTrue(2 <= repository.findAll().size());
    }

    @Test
    void shouldCreateNewTVShow() {
        TvShow tokyoGhoul = new TvShow();
        tokyoGhoul.setTvShowName("Tokyo Ghoul");
        TvShow show = repository.save(tokyoGhoul);
        assertEquals(4, show.getTvShowId());
    }

    @Test
    void shouldFindRickAndMorty() {
        TvShow rickAndMorty = repository.findById(1).orElse(null);
        assertNotNull(rickAndMorty);
        assertEquals("Rick and Morty", rickAndMorty.getTvShowName());
    }

    @Test
    void shouldUpdateSuits() {
        TvShow suits = repository.findById(2).orElse(null);
        assertNotNull(suits);
        suits.setTvShowName("Lawsuits");
        repository.save(suits);

        TvShow saved = repository.findById(2).orElse(null);
        assertNotNull(saved);
        assertEquals("Lawsuits", saved.getTvShowName());
    }

    @Test
    void shouldDeleteStrangerThings() {
        TvShow strangerThings = repository.findById(3).orElse(null);
        assertNotNull(strangerThings);
        repository.delete(strangerThings);

        TvShow rm = repository.findById(3).orElse(null);
        assertNull(rm);
    }
}