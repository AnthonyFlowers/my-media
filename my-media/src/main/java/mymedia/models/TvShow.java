package mymedia.models;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
public class TvShow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int tvShowId;

    @Size(max = 256)
    private String tvShowName;

    @OneToMany(mappedBy = "tvShowId")
    private List<TvShowSeason> seasons;

    public int getTvShowId() {
        return tvShowId;
    }

    public void setTvShowId(int tvShowId) {
        this.tvShowId = tvShowId;
    }

    public String getTvShowName() {
        return tvShowName;
    }

    public void setTvShowName(String tvShowName) {
        this.tvShowName = tvShowName;
    }
}
