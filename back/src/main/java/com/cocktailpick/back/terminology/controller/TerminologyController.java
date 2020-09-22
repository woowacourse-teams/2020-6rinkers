package com.cocktailpick.back.terminology.controller;

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

import com.cocktailpick.back.terminology.domain.Terminology;
import com.cocktailpick.back.terminology.dto.TerminologyResponse;
import com.cocktailpick.back.terminology.service.TerminologyService;
import com.cocktailpick.back.terminology.dto.TerminologyRequest;
import lombok.RequiredArgsConstructor;

@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/api/terminologies")
@RestController
public class TerminologyController {
	private final TerminologyService terminologyService;

	@GetMapping
	public ResponseEntity<List<TerminologyResponse>> findTerminologies() {
		return ResponseEntity.ok(terminologyService.findAllTerminologies());
	}

	@GetMapping("/{id}")
	public ResponseEntity<TerminologyResponse> findTerminology(@PathVariable Long id) {
		return ResponseEntity.ok(terminologyService.findTerminology(id));
	}

	@PostMapping
	public ResponseEntity<Void> save(@RequestBody @Valid TerminologyRequest terminologyRequest) {
		Terminology terminology = terminologyRequest.toTerminology();
		Long persistId = terminologyService.save(terminology);
		return ResponseEntity.created(URI.create("/api/terminologies/" + persistId)).build();
	}

	@PostMapping("/upload/csv")
	public ResponseEntity<Void> saveTerminologiesByCsv(@RequestPart MultipartFile file) {
		terminologyService.saveAll(file);
		return ResponseEntity.created(URI.create("/api/terminologies")).build();
	}

	@PutMapping("/{id}")
	public ResponseEntity<Void> update(@RequestBody @Valid TerminologyRequest terminologyRequest,
		@PathVariable Long id) {
		terminologyService.update(terminologyRequest.toTerminology(), id);
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		terminologyService.delete(id);
		return ResponseEntity.noContent().build();
	}
}
