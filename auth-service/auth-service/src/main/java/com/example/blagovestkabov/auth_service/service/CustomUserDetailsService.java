package com.example.blagovestkabov.auth_service.service;

import com.example.blagovestkabov.auth_service.model.CustomUserDetails;
import com.example.blagovestkabov.auth_service.model.User;
import com.example.blagovestkabov.auth_service.repository.AuthenticationRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    private final AuthenticationRepository authenticationRepository;

    public CustomUserDetailsService(AuthenticationRepository authenticationRepository) {
        this.authenticationRepository = authenticationRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = authenticationRepository.findByUsername(username);
        if (Objects.isNull(user)) {
            throw  new UsernameNotFoundException("User is not available");
        }
        return new CustomUserDetails(user);
    }

}
