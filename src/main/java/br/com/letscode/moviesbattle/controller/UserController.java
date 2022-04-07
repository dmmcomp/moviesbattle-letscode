package br.com.letscode.moviesbattle.controller;

import br.com.letscode.moviesbattle.entity.Game;
import br.com.letscode.moviesbattle.entity.User;
import br.com.letscode.moviesbattle.service.MatchService;
import br.com.letscode.moviesbattle.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/user")
public class UserController {

    private final MatchService matchService;
    private final UserService userService;
    private final PasswordEncoder encoder;

    @Autowired
    public UserController(MatchService matchService, UserService userService, PasswordEncoder encoder) {
        this.matchService = matchService;
        this.userService = userService;
        this.encoder = encoder;
    }

    @PostMapping
    public User createUser(@RequestBody User user){
        return userService.save(user);
    }

}
