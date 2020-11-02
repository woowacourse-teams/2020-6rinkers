package com.cocktailpick;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;

import com.cocktailpick.api.config.security.AppProperties;

@SpringBootApplication
@EnableConfigurationProperties(AppProperties.class)
@EnableCaching
public class BackApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackApplication.class, args);
	}

}
