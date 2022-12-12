package mymedia.data;

import mymedia.models.AppUserTvShow;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppUserTvShowRepository extends JpaRepository<AppUserTvShow, Integer> {

    Page<AppUserTvShow> findByUserUsername(Pageable pageable, String username);

}
