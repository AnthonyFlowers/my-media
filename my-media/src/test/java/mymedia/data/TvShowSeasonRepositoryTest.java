package mymedia.data;

import mymedia.models.TvShowSeason;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TvShowSeasonRepositoryTest {
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    TvShowSeasonRepository repository;

    private boolean isSetUp = false;

    @BeforeEach
    public void setUp() {
        if (!isSetUp) {
            KnownGoodState.setKnownGoodState(jdbcTemplate);
            isSetUp = true;
        }
    }

    @Test
    void shouldFindAtLeast5TvShowSeasons() {
        assertTrue(5 <= repository.findAll().size());
    }

    @Test
    void shouldAddTvShowSeason() {
        TvShowSeason newSeason = new TvShowSeason();
        newSeason.setTvShowId(1);
        newSeason.setTvShowSeasonIndex(4);
        TvShowSeason season = repository.save(newSeason);
        assertEquals(7, season.getTvShowSeasonId());
    }

    @Test
    void shouldFindTvShowSeasonById() {
        TvShowSeason season = repository.findById(6).orElse(null);
        assertNotNull(season);
        assertEquals(3, season.getTvShowSeasonIndex());
    }

    @Test
    void shouldUpdateTvShowSeason() {
        TvShowSeason season = repository.findById(4).orElse(null);
        assertNotNull(season);
        season.setTvShowSeasonIndex(4);
        repository.save(season);

        TvShowSeason saved = repository.findById(4).orElse(null);
        assertNotNull(saved);
        assertEquals(4, saved.getTvShowSeasonIndex());
    }

    @Test
    void shouldDeleteTvShowSeason() {
        TvShowSeason season = repository.findById(5).orElse(null);
        assertNotNull(season);
        repository.delete(season);

        TvShowSeason rm = repository.findById(5).orElse(null);
        assertNull(rm);
    }
}