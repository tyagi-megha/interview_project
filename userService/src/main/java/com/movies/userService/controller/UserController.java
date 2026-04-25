package com.movies.userService.controller;

import com.movies.userService.config.MovieUserDetailsService;
import com.movies.userService.dto.UserDto;
import com.movies.userService.model.User;
import com.movies.userService.request.CreateUserRequest;
import com.movies.userService.request.UpdateUserRequest;
import com.movies.userService.request.UsernameRequest;
import com.movies.userService.response.ApiResponse;
import com.movies.userService.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/users")
public class UserController {
    private final IUserService userService;

    private final MovieUserDetailsService movieUserDetailsService;

    @PostMapping("/username")
    public UserDto getUserByUsername(@RequestBody UsernameRequest request){
        System.out.println("username " + request.getUsername());
        User user = movieUserDetailsService.getUserByUsername(request.getUsername());
        System.out.println("user:" + user.getId() + user.getUsername()+
                user.getPassword() + user.getEmail() );
        return userService.convertToDto(user);

    }

    @GetMapping("/user/{userId}/user")
    public ResponseEntity<ApiResponse> getUserByID(@PathVariable Long userId){
        User user = userService.getUserById(userId);
        UserDto userDto = userService.convertToDto(user);
        return ResponseEntity.ok(new ApiResponse("User found", userDto));
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> createUser(@RequestBody CreateUserRequest request){
        User user = userService.createUser(request);
        UserDto userDto = userService.convertToDto(user);
        return ResponseEntity.ok(new ApiResponse("User created", userDto));
    }

    @PutMapping("/user/{userId}/update")
    public ResponseEntity<ApiResponse> updateUser(@RequestBody UpdateUserRequest request, @PathVariable Long userId){
        User user = userService.updateUser(request, userId);
        UserDto userDto = userService.convertToDto(user);
        return ResponseEntity.ok(new ApiResponse("User updated", userDto));
    }

    @DeleteMapping("/user/{userId}/delete")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Long userId){
        userService.deleteUser(userId);
        return ResponseEntity.ok(new ApiResponse("User deleted", userId));
    }

    @GetMapping("all")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> userDtos = userService.getAllUsers().stream()
                .map(userService::convertToDto)
                .toList();
        return ResponseEntity.ok(userDtos);
    }

}
