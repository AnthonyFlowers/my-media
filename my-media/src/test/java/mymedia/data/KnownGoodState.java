package mymedia.data;

import org.springframework.jdbc.core.JdbcTemplate;

public class KnownGoodState {

    public static void setKnownGoodState(JdbcTemplate jdbcTemplate) {
        jdbcTemplate.update("call set_known_good_state()");
    }
}
