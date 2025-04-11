package com.blagovestkabov.auth_service.controller;

import com.blagovestkabov.auth_service.model.User;
import com.blagovestkabov.auth_service.repository.AuthenticationRepository;
import com.blagovestkabov.auth_service.service.AuthenticationService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    private final AuthenticationRepository authenticationRepository;

    public AuthenticationController(AuthenticationService authenticationService, AuthenticationRepository authenticationRepository) {
        this.authenticationService = authenticationService;
        this.authenticationRepository = authenticationRepository;
    }

    @PostMapping(value = "/register")
    public User registerUser(@RequestBody User user) {
       return authenticationService.registerUser(user);
    }

    @PostMapping(value = "/login")
    public String login(@RequestBody User user) {
        return authenticationService.verifyUser(user);
    }

    @GetMapping(value = "/test")
    public String helloWorld(){
        return "hello from blago. testing the authentication permissions";
    }
}
