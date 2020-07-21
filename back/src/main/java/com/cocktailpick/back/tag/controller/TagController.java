package com.cocktailpick.back.tag.controller;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cocktailpick.back.tag.service.TagService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/tags")
public class TagController {
	private final TagService tagService;

	@PostMapping("/upload/csv")
	public ResponseEntity<Void> addTagsByCsv(@RequestPart MultipartFile file) {
		tagService.saveAll(file);

		return ResponseEntity.created(URI.create("/api/tags")).build();
	}
}
