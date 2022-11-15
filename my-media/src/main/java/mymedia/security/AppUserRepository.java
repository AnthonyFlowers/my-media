package mymedia.security;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Collection;
import java.util.List;

@Repository
public class AppUserRepository {

    private final JdbcTemplate jdbcTemplate;

    public AppUserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional
    public AppUser findByUsername(String username) {
        List<String> roles = getRolesByUsername(username);

        final String sql = "select app_user_id, username, password_hash, enabled " +
                "from app_user " +
                "where username = ?;";
        return jdbcTemplate.query(sql, new AppUserMapper(roles), username)
                .stream()
                .findFirst().orElse(null);
    }

    @Transactional
    public AppUser create(AppUser user) {
        final String sql = "insert into app_user (username, password_hash) values (?, ?);";

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            return ps;
        }, keyHolder);
        if (rowsAffected <= 0) {
            return null;
        }
        user.setAppUserId(keyHolder.getKey().intValue());
        updateRoles(user);
        return user;
    }

    @Transactional
    public void update(AppUser user) {
        final String sql = "update app_user set " +
                "username = ?, " +
                "password_hash = ?, " +
                "enabled = ? " +
                "where app_user_id = ?;";
        jdbcTemplate.update(sql,
                user.getUsername(),
                user.getPassword(),
                user.isEnabled(),
                user.getAppUserId());
        updateRoles(user);
    }

    private void updateRoles(AppUser user) {
        jdbcTemplate.update("delete from app_user_role where app_user_id = ?;", user.getAppUserId());
        Collection<GrantedAuthority> authorities = user.getAuthorities();

        if (authorities == null) {
            return;
        }
        for (GrantedAuthority role : authorities) {
            String sql = "insert into app_user_role (app_user_id, app_role_id) " +
                    "select ?, app_role_id from app_role where `name` = ?;";
            jdbcTemplate.update(sql, user.getAppUserId(), role.getAuthority());
        }
    }

    private List<String> getRolesByUsername(String username) {
        final String sql = "select ar.name " +
                "from app_role ar join app_user_role aur join app_user au " +
                "on ar.app_role_id = aur.app_role_id " +
                "and aur.app_user_id = au.app_user_id " +
                "where au.username = ?;";
        return jdbcTemplate.query(
                sql,
                (rs, rowId) -> rs.getString("name"),
                username);
    }

    public boolean delete(AppUser user) {
        final String sql = "delete from app_user where app_user_id = ?;";
        int rowsAffected = jdbcTemplate.update(sql, user.getAppUserId());
        return rowsAffected > 0;
    }
}
