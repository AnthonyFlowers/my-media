package mymedia.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Entity
public class TvShowEpisode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int tvShowEpisodeId;
    int tvShowSeasonId;
    @Min(0)
    int tvShowEpisodeIndex;
    @Size(max = 256, message = "TV Show episode name can't exceed 255 characters")
    String tvShowEpisodeName;
    @Min(0)
    int tvShowEpisodeLength;

    public int getTvShowEpisodeId() {
        return tvShowEpisodeId;
    }

    public void setTvShowEpisodeId(int tvShowEpisodeId) {
        this.tvShowEpisodeId = tvShowEpisodeId;
    }

    public int getTvShowSeasonId() {
        return tvShowSeasonId;
    }

    public void setTvShowSeasonId(int tvShowSeasonId) {
        this.tvShowSeasonId = tvShowSeasonId;
    }

    public int getTvShowEpisodeIndex() {
        return tvShowEpisodeIndex;
    }

    public void setTvShowEpisodeIndex(int tvShowEpisodeIndex) {
        this.tvShowEpisodeIndex = tvShowEpisodeIndex;
    }

    public String getTvShowEpisodeName() {
        return tvShowEpisodeName;
    }

    public void setTvShowEpisodeName(String tvShowEpisodeName) {
        this.tvShowEpisodeName = tvShowEpisodeName;
    }

    public int getTvShowEpisodeLength() {
        return tvShowEpisodeLength;
    }

    public void setTvShowEpisodeLength(int tvShowEpisodeLength) {
        this.tvShowEpisodeLength = tvShowEpisodeLength;
    }
}
