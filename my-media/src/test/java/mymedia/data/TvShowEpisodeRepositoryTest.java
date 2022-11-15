package mymedia.data;

import mymedia.models.TvShowEpisode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TvShowEpisodeRepositoryTest {
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    TvShowEpisodeRepository repository;

    private boolean isSetUp = false;

    @BeforeEach
    public void setUp() {
        if (!isSetUp) {
            KnownGoodState.setKnownGoodState(jdbcTemplate);
            isSetUp = true;
        }
    }

    @Test
    void shouldFindAtLeast7TvShowEpisodes() {
        assertTrue(7 <= repository.findAll().size());
    }

    @Test
    void shouldCreateNewTvShowEpisode() {
        TvShowEpisode episode = new TvShowEpisode();
        episode.setTvShowEpisodeIndex(5);
        episode.setTvShowSeasonId(1);
        TvShowEpisode saved = repository.save(episode);
        assertEquals(9, saved.getTvShowEpisodeId());
    }

    @Test
    void shouldFindTvShowEpisode() {
        TvShowEpisode episode = repository.findById(2).orElse(null);
        assertNotNull(episode);
        assertEquals(2, episode.getTvShowEpisodeIndex());
    }

    @Test
    void shouldUpdateTvShowEpisode() {
        TvShowEpisode episode = repository.findById(4).orElse(null);
        assertNotNull(episode);
        episode.setTvShowEpisodeIndex(5);
        TvShowEpisode saved = repository.save(episode);
        assertEquals(5, saved.getTvShowEpisodeIndex());
    }

    @Test
    void shouldDeleteTvShowEpisode() {
        TvShowEpisode episode = repository.findById(7).orElse(null);
        assertNotNull(episode);
        repository.delete(episode);

        TvShowEpisode rm = repository.findById(7).orElse(null);
        assertNull(rm);
    }

}