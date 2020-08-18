package com.cocktailpick.back.tag.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cocktailpick.back.tag.dto.TagRequest;
import com.cocktailpick.back.tag.dto.TagResponse;
import com.cocktailpick.back.tag.service.TagService;
import lombok.RequiredArgsConstructor;

@CrossOrigin("*")
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

	@GetMapping
	public ResponseEntity<List<TagResponse>> findTags() {
		return ResponseEntity.ok(tagService.findAllTags());
	}

	@PostMapping
	public ResponseEntity<Void> createTag(@RequestBody @Valid TagRequest tagRequest) {
		Long tagId = tagService.createTag(tagRequest);

		return ResponseEntity.created(URI.create("/api/tags/" + tagId)).build();
	}

	@PutMapping("{id}")
	public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody @Valid TagRequest tagRequest) {
		tagService.update(id, tagRequest);

		return ResponseEntity.ok().build();
	}

	@DeleteMapping("{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		tagService.delete(id);

		return ResponseEntity.noContent().build();
	}
}
