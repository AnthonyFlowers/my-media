package mymedia.security;

import mymedia.data.KnownGoodState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class AppUserRepositoryTest {

    @Autowired
    AppUserRepository repository;

    @Autowired
    JdbcTemplate jdbcTemplate;

    private boolean isSetUp = false;

    @BeforeEach
    public void setUp() {
        if (!isSetUp) {
            KnownGoodState.setKnownGoodState(jdbcTemplate);
            isSetUp = true;
        }
    }

    @Test
    void shouldFindAtLeast1User() {
        assertTrue(2 <= repository.findAll().size());
    }

    @Test
    void shouldCreateNewUser() {
        AppUser user = new AppUser();
        user.setEnabled(true);
        user.setPassword("password"); // should not be saved
        user.setPasswordHash("123");
        user.setUsername("");

        AppUser savedUser = repository.save(user);
        assertEquals(4, savedUser.getAppUserId());
        assertNull(savedUser.getPassword()); // password should be ignored when saving
    }

    @Test
    void shouldFindUserJohnSmith() {
        AppUser user = repository.findById(1).orElse(null);
        assertNotNull(user);
        assertEquals("johnsmith", user.getUsername());
    }

    @Test
    void shouldUpdateUserJaneDoe() {
        AppUser user = repository.findById(2).orElse(null);
        assertNotNull(user);
        user.setUsername("jane_doe");
        repository.save(user);

        AppUser updated = repository.findById(2).orElse(null);
        assertNotNull(updated);
        assertEquals("jane_doe", updated.getUsername());
    }

    @Test
    void shouldDeleteUserAshKetchum() {
        AppUser user = repository.findById(3).orElse(null);
        assertNotNull(user);

        repository.delete(user);
        AppUser rm = repository.findById(3).orElse(null);
        assertNull(rm);
    }
}