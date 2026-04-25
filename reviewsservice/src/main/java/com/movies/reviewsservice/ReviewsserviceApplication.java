package com.movies.reviewsservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.movies.reviewsservice.integration")
public class ReviewsserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReviewsserviceApplication.class, args);
	}

}
