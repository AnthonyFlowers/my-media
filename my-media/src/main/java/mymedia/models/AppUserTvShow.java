package mymedia.models;

import mymedia.security.AppUser;

import javax.persistence.*;

@Entity
@Table(name = "app_user_tv_show")
public class AppUserTvShow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int appUserTvShowId;

    @OneToOne
    @JoinColumn(name = "app_user_id", referencedColumnName = "app_user_id")
    private AppUser user;

    @ManyToOne
    @JoinColumn(name = "tv_show_id")
    private TvShow tvShow;

    private int season;
    private int episode;
    private int watchCount;

    public AppUserTvShow() {}

    public AppUserTvShow(TvShow tvShow, AppUser appUser) {
        this.tvShow = tvShow;
        this.user = appUser;
    }

    public int getAppUserTvShowId() {
        return appUserTvShowId;
    }

    public void setAppUserTvShowId(int appUserTvShowId) {
        this.appUserTvShowId = appUserTvShowId;
    }

    public int getUserId() {
        return user.getAppUserId();
    }

    public void setUser(AppUser user) {
        this.user = user;
    }

    public TvShow getTvShow() {
        return tvShow;
    }

    public void setTvShow(TvShow tvShow) {
        this.tvShow = tvShow;
    }

    public int getSeason() {
        return season;
    }

    public void setSeason(int season) {
        this.season = season;
    }

    public int getEpisode() {
        return episode;
    }

    public void setEpisode(int episode) {
        this.episode = episode;
    }

    public int getWatchCount() {
        return watchCount;
    }

    public void setWatchCount(int watchCount) {
        this.watchCount = watchCount;
    }
}
