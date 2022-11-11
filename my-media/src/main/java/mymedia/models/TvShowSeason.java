package mymedia.models;

import javax.persistence.*;

@Entity
public class TvShowSeason {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int tvShowSeasonId;

    private int tvShowId;
    private int tvShowSeasonIndex;

    public int getTvShowSeasonId() {
        return tvShowSeasonId;
    }

    public void setTvShowSeasonId(int tvShowSeasonId) {
        this.tvShowSeasonId = tvShowSeasonId;
    }

    public int getTvShowId() {
        return tvShowId;
    }

    public void setTvShowId(int tvShowId) {
        this.tvShowId = tvShowId;
    }

    public int getTvShowSeasonIndex() {
        return tvShowSeasonIndex;
    }

    public void setTvShowSeasonIndex(int tvShowSeasonIndex) {
        this.tvShowSeasonIndex = tvShowSeasonIndex;
    }
}
