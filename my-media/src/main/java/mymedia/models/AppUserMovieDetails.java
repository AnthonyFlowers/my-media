package mymedia.models;

import mymedia.security.AppUser;

import javax.persistence.*;

@Entity
@Table(name = "app_user_movie")
public class AppUserMovieDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int appUserMovieId;

    @OneToOne
    @JoinColumn(name = "app_user_id", referencedColumnName = "app_user_id")
    private AppUser appUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id")
    private Movie movie;

    private boolean watched;

    public int getAppUserMovieId() {
        return appUserMovieId;
    }

    public void setAppUserMovieId(int appUserMovieId) {
        this.appUserMovieId = appUserMovieId;
    }

    public AppUser getAppUser() {
        return appUser;
    }

    public void setAppUser(AppUser appUser) {
        this.appUser = appUser;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public boolean isWatched() {
        return watched;
    }

    public void setWatched(boolean watched) {
        this.watched = watched;
    }
}
