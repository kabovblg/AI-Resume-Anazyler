package com.example.blagovestkabov.auth_service.repository;

import com.example.blagovestkabov.auth_service.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthenticationRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
