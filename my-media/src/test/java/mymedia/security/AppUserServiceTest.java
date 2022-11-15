package mymedia.security;

import mymedia.domain.ActionStatus;
import mymedia.domain.Result;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@SpringBootTest
class AppUserServiceTest {

    @MockBean
    AppUserRepository repository;
    @Autowired
    AppUserService service;

    @Test
    void shouldFindUser() {
        AppUser user = getTestUser();
        when(repository.findByUsername("johnsmith")).thenReturn(user);
        UserDetails userDetails = service.loadUserByUsername("johnsmith");
        assertEquals(user.getUsername(), userDetails.getUsername());
    }

    @Test
    void shouldCreateUser() {
        AppUser user = getTestUser();
        when(repository.create(any())).thenReturn(user);
        Result<AppUser> result = service.create("johnsmith", "P@ssw0rd");
        assertTrue(result.isSuccess());
        assertEquals(user, result.getPayload());
    }

    @Test
    void shouldNotCreateUserDuplicateUsername() {
        doThrow(new DuplicateKeyException("")).when(repository).create(any());
        Result<AppUser> result = service.create("johnsmith", "P@ssw0rd");
        assertFalse(result.isSuccess());
        assertEquals(1, result.getMessages().size());
    }

    @Test
    void shouldNotCreateUserInvalidPassword() {
        Result<AppUser> result = service.create("johnsmith", "password");
        assertFalse(result.isSuccess());
        assertEquals(1, result.getMessages().size());
    }

    @Test
    void shouldUpdateUser() {
        AppUser user = getTestUser();
        Result<AppUser> result = service.update(user);
        assertEquals(user, result.getPayload());
    }

    @Test
    void shouldNotUpdateUser() {
        AppUser user = getTestUser();
        doThrow(new DuplicateKeyException("")).when(repository).update(user);
        Result<AppUser> result = service.update(user);
        assertFalse(result.isSuccess());
        assertEquals(1, result.getMessages().size());
    }

    private static AppUser getTestUser() {
        return new AppUser(1, "johnsmith",
                "$2a$10$ntB7CsRKQzuLoKY3rfoAQen5nNyiC/U60wBsWnnYrtQQi8Z3IZzQa",
                true, List.of("USER"));
    }
}