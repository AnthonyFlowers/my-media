package mymedia.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

public class KnownGoodState {

    public static void setKnownGoodState(JdbcTemplate jdbcTemplate) {
        jdbcTemplate.update("call set_known_good_state()");
    }
}
