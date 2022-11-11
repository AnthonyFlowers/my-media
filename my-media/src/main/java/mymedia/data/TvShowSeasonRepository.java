package mymedia.data;

import mymedia.models.TvShowSeason;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TvShowSeasonRepository extends JpaRepository<TvShowSeason, Integer> {
}
