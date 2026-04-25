package com.movies.reviewsservice.controller;

import com.movies.reviewsservice.dto.UserDto;
import com.movies.reviewsservice.integration.UserServiceClient;
import com.movies.reviewsservice.request.UsernameRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//test controller to test if feignclient working
@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/test")
public class UserServiceClientTestController {

    private final UserServiceClient userServiceClient;


    @PostMapping("/user/username")
    public ResponseEntity<UserDto> testUserFetch(@RequestBody UsernameRequest request) {

        UserDto user = userServiceClient.getUserByUsername(request);
        System.out.println("user data" + user.getId() + " " + user.getUsername() +
                " " + user.getEmail());
        return ResponseEntity.ok(user);
    }
}
