package com.movies.reviewsservice.config;


import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

//I have this because after adding JWT to movie service, that service expects every call to be authenticated
// hence this config helps in attaching jwt to request to feign client
@Configuration
public class FeignConfig {

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null && authentication.getCredentials() instanceof String jwt) {
                // You already validated the token in JwtAuthenticationFilter
                // If you stored the raw JWT as credentials, you can forward it here
                requestTemplate.header("Authorization", "Bearer " + jwt);

            }
        };
    }
}
