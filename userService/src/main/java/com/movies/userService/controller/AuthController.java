package com.movies.userService.controller;

import com.movies.userService.config.MovieUserDetailsService;
import com.movies.userService.config.jwt.JwtUtil;
import com.movies.userService.model.User;
import com.movies.userService.repository.UserRepository;
import com.movies.userService.request.LoginRequest;
import com.movies.userService.response.ApiResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/auth")
public class AuthController {
    private final JwtUtil jwtUtil;
    private final MovieUserDetailsService movieUserDetailsService;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;


    @PostMapping("/login")
    public ResponseEntity<?> createToken(@RequestBody LoginRequest authRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
        );

        final UserDetails userDetails = movieUserDetailsService.loadUserByUsername(authRequest.getUsername());
       // 3. Fetch user entity from DB to get userId
        User user =  Optional.ofNullable(userRepository.findByEmail(authRequest.getUsername()))
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        final String jwt = jwtUtil.generateToken(userDetails, user.getId());

        return ResponseEntity.ok(new ApiResponse(jwt, null));
    }
}
