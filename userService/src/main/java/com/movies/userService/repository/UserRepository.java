package com.movies.userService.repository;

import com.movies.userService.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String userName);
    boolean existsByEmail(String email);

    User findByEmail(String email);
}
