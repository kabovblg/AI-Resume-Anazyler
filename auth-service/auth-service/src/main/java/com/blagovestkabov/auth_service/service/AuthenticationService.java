package com.blagovestkabov.auth_service.service;

import com.blagovestkabov.auth_service.model.User;
import com.blagovestkabov.auth_service.repository.AuthenticationRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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
        if (authentication.isAuthenticated())
            return jwtService.generateToken(user);
        return "fail with the login";
    }
}
