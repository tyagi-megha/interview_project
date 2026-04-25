package com.movies.reviewsservice.integration;

import com.movies.reviewsservice.dto.UserDto;
import com.movies.reviewsservice.request.UsernameRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
//no more needed, not in use can be decoed later
@FeignClient(name = "user-service", url = "http://localhost:9093") // adjust port
public interface UserServiceClient {

    @PostMapping("${api.prefix}/users/username")
    UserDto getUserByUsername(@RequestBody UsernameRequest request);

}
