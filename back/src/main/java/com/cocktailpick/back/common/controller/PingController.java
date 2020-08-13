package com.cocktailpick.back.common.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ping")
public class PingController {

	@GetMapping
	ResponseEntity<Void> ping() {
		return ResponseEntity.ok().build();
	}
}
