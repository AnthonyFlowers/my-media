package mymedia.data;

import mymedia.models.TvShowEpisode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TvShowEpisodeRepository extends JpaRepository<TvShowEpisode, Integer> {
}
