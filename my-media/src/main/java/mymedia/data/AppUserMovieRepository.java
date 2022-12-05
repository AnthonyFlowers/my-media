package mymedia.data;

import mymedia.models.AppUserMovie;
import mymedia.security.AppUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppUserMovieRepository extends JpaRepository<AppUserMovie, Integer> {

    Page<AppUserMovie> findByUserUsername(Pageable pageable, String username);

    Page<AppUserMovie> findAppUserMoviesByUser(Pageable pageable, AppUser user);
}