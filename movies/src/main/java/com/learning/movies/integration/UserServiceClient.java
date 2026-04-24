package com.learning.movies.integration;

import com.learning.movies.dto.UserDto;
import com.learning.movies.request.UsernameRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "user-service", url = "http://localhost:9093") // adjust port
public interface UserServiceClient {

    @PostMapping("${api.prefix}/users/username")
    UserDto getUserByUsername(@RequestBody UsernameRequest request);

}
