package com.cocktailpick.back;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.cocktailpick.back.config.AppProperties;

@SpringBootApplication
@EnableConfigurationProperties(
	AppProperties.class)
public class BackApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackApplication.class, args);
	}

}
