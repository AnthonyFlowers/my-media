package mymedia.security;

import mymedia.domain.Action;
import mymedia.domain.Result;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppUserService implements UserDetailsService {
    private final AppUserRepository repository;
    private final PasswordEncoder encoder;

    public AppUserService(AppUserRepository repository, PasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user = repository.findByUsername(username);
        if (user == null || !user.isEnabled()) {
            throw new UsernameNotFoundException(username + " not found");
        }
        return user;
    }

    public Result<AppUser> create(String username, String password) {
        Result<AppUser> result = validate(username, password);
        if (!result.isSuccess()) {
            return result;
        }
        AppUser user = new AppUser(0, username, password, true, List.of("USER"));
        try {
            user = repository.create(user);
            result.setPayload(user);
        } catch (DuplicateKeyException e){
            result.addMessage(Action.INVALID, "That username already exists");
        }
        return result;
    }

    private Result<AppUser> validate(String username, String password) {
        Result<AppUser> result = new Result<>();
        if (username == null || username.isBlank()) {
            result.addMessage(Action.INVALID, "username is required");
            return result;
        }
        if (password == null) {
            result.addMessage(Action.INVALID, "password is required");
            return result;
        }
        if (username.length() > 50) {
            result.addMessage(Action.INVALID, "username must be less than 50 characters");
        }

        if (!isValidPassword(password)) {
            result.addMessage(Action.INVALID,
                    "password must be at least 8 characters, " +
                            "have one digit, a capital letter, a lowercase letter, " +
                            "and a non-digit/non-letter character");
        }
        return null;
    }

    private boolean isValidPassword(String password) {
        if (password.length() < 8) {
            return false;
        }
        int digits = 0;
        int letters = 0;
        int others = 0;
        for (char c : password.toCharArray()) {
            if (Character.isLetter(c)) {
                letters++;
            } else if (Character.isDigit(c)) {
                digits++;
            } else {
                others++;
            }
        }
        return digits > 0 && letters > 0 && others > 0;
    }
}
