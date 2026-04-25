package com.movies.userService.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {

    @NotBlank(message = "Invalid login credentials")
    private String username;//is basically an email
    @NotBlank(message = "Invalid login credentials")
    private String password;
}
