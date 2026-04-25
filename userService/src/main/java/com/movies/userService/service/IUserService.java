package com.movies.userService.service;

import com.movies.userService.dto.UserDto;
import com.movies.userService.model.User;
import com.movies.userService.request.CreateUserRequest;
import com.movies.userService.request.UpdateUserRequest;

import java.util.List;

public interface IUserService {

    User createUser(CreateUserRequest request);

    User updateUser(UpdateUserRequest request, Long userId);

    User getUserById(Long userId);

    void deleteUser(Long userId);

    UserDto convertToDto(User user);

    List<User> getAllUsers();

    //User getAuthenticatedUSer();
}
