package mymedia.domain;

import mymedia.data.AppUserMovieRepository;
import mymedia.models.AppUserMovie;
import mymedia.security.AppUser;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@SpringBootTest
class AppUserMovieServiceTest {

    @MockBean
    AppUserMovieRepository repository;

    @Autowired
    AppUserMovieService service;

    @Test
    void findByUserMovieIdAndUser() {
        AppUserMovie expected = new AppUserMovie();
        when(repository.findByAppUserMovieIdAndUser(anyInt(), any(AppUser.class)))
                .thenReturn(expected);
        AppUserMovie actual = service.findByUserMovieIdAndUser(1, new AppUser());
        assertEquals(expected, actual);
    }
}