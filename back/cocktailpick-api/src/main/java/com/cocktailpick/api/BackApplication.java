package com.cocktailpick.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.cocktailpick.api.config.security.AppProperties;

@SpringBootApplication
@EnableConfigurationProperties(AppProperties.class)
public class BackApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackApplication.class, args);
	}

}
