package mymedia.security;

import mymedia.domain.ActionStatus;
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
        password = encoder.encode(password);
        AppUser user = new AppUser(0, username, password, true, List.of("USER"));
        try {
            user = repository.create(user);
        } catch (DuplicateKeyException e){
            result.addMessage(ActionStatus.INVALID, "That username already exists");
        }
        if(result.isSuccess()){
            result.setPayload(user);
        }
        return result;
    }

    public Result<AppUser> update(AppUser user) {
        Result<AppUser> result = validate(user.getUsername(), user.getPassword());
        user.setPassword(encoder.encode(user.getPassword()));
        try {
            repository.update(user);
        } catch (DuplicateKeyException e){
            result.addMessage(ActionStatus.INVALID, "That username already exists");
        }
        if(result.isSuccess()) {
            result.setPayload(user);
        }
        return result;
    }


    private Result<AppUser> validate(String username, String password) {
        Result<AppUser> result = new Result<>();
        if (username == null || username.isBlank()) {
            result.addMessage(ActionStatus.INVALID, "username is required");
            return result;
        }
        if (password == null) {
            result.addMessage(ActionStatus.INVALID, "password is required");
            return result;
        }
        if (username.length() > 50) {
            result.addMessage(ActionStatus.INVALID, "username must be less than 50 characters");
        }

        if (!isValidPassword(password)) {
            result.addMessage(ActionStatus.INVALID,
                    "password must be at least 8 characters, " +
                            "have one digit, a letter, and a non-digit/non-letter character");
        }
        return result;
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