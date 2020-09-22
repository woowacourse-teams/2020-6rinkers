package com.cocktailpick.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.cocktailpick.api.config.security.AppProperties;

@SpringBootApplication(scanBasePackages = {"com.cocktailpick.core", "com.cocktailpick.api"})
@EnableConfigurationProperties(AppProperties.class)
public class ModuleApiApplication {
	public static void main(String[] args) {
		SpringApplication.run(ModuleApiApplication.class, args);
	}
}
