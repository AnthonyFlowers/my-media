package mymedia.security;

import mymedia.domain.Result;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class AuthController {

    private final AuthenticationManager authentication;
    private final JwtConverter jwtConverter;
    private final AppUserService service;

    public AuthController(AuthenticationManager authentication, JwtConverter jwtConverter, AppUserService service) {
        this.authentication = authentication;
        this.jwtConverter = jwtConverter;
        this.service = service;
    }

    @PostMapping("/create_account")
    public ResponseEntity<?> createAccount(@RequestBody Map<String, String> credentials){
        String username = credentials.get("username");
        String password = credentials.get("password");

        Result<AppUser> result = service.create(username, password);
        if(!result.isSuccess()) {
            return new ResponseEntity<>(result.getMessages(), HttpStatus.BAD_REQUEST);
        }

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("jwt", jwtConverter.getTokenFromUser(result.getPayload()));
        return new ResponseEntity<>(map, HttpStatus.CREATED);
    }
}
