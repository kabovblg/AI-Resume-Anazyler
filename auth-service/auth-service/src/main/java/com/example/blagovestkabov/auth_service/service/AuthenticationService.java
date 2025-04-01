package com.example.blagovestkabov.auth_service.service;

import com.example.blagovestkabov.auth_service.config.SecurityConfig;
import com.example.blagovestkabov.auth_service.model.User;
import com.example.blagovestkabov.auth_service.repository.AuthenticationRepository;
import org.json.JSONObject;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
public class AuthenticationService {

    private final AuthenticationRepository authenticationRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;


    public AuthenticationService(AuthenticationRepository authenticationRepository, BCryptPasswordEncoder bCryptPasswordEncoder, AuthenticationManager authenticationManager, JwtService jwtService) {
        this.authenticationRepository = authenticationRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public User registerUser(User user) {
        if (user.getRole() == null) {
            user.setRole("USER");
        }
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return authenticationRepository.save(user);
    }

    public String verifyUser(User user) {
        Authentication authentication
                = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
//        var loggedUser = authenticationRepository.findByUsername(user.getUsername());
        if (authentication.isAuthenticated())
            return jwtService.generateToken(user);
        return "fail with the login";
    }
}
