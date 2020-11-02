package com.cocktailpick.api.tag.controller;

import java.io.IOException;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cocktailpick.api.csv.OpenCsvReader;
import com.cocktailpick.api.csv.TagCsvReader;
import com.cocktailpick.core.tag.domain.TagType;
import com.cocktailpick.core.tag.dto.TagRequest;
import com.cocktailpick.core.tag.dto.TagResponse;
import com.cocktailpick.core.tag.service.TagService;
import lombok.RequiredArgsConstructor;

@CrossOrigin("*")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/tags")
public class TagController {
	private final TagService tagService;

	@PostMapping("/upload/csv")
	public ResponseEntity<Void> addTagsByCsv(@RequestPart MultipartFile file) throws IOException {
		TagCsvReader tagCsvReader = new TagCsvReader(OpenCsvReader.from(file.getInputStream()));
		tagService.saveAll(tagCsvReader.getTagRequests());

		return ResponseEntity.created(URI.create("/api/tags")).build();
	}

	@GetMapping
	public ResponseEntity<List<TagResponse>> findTags(@RequestParam(required = false) TagType tagType,
		@RequestParam(required = false) Integer size, @RequestParam(defaultValue = "false") boolean random) {
		return ResponseEntity.ok(tagService.findTags(tagType, size, random));
	}

	@PostMapping
	public ResponseEntity<Void> createTag(@RequestBody @Valid TagRequest tagRequest) {
		Long tagId = tagService.createTag(tagRequest);

		return ResponseEntity.created(URI.create("/api/tags/" + tagId)).build();
	}

	@PutMapping("{id}")
	public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody @Valid TagRequest tagRequest) {
		tagService.update(id, tagRequest);

		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		tagService.delete(id);

		return ResponseEntity.noContent().build();
	}
}
