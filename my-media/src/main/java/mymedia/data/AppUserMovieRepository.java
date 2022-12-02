package mymedia.data;

import mymedia.models.AppUserMovieDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppUserMovieRepository extends JpaRepository<AppUserMovieDetails, Integer> {

    Page<AppUserMovieDetails> findByAppUserUsername(Pageable pageable, String username);
}
