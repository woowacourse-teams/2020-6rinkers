package com.cocktailpick.back.dictionary.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cocktailpick.back.dictionary.domain.Terminology;
import com.cocktailpick.back.dictionary.dto.TerminologyResponse;
import com.cocktailpick.back.dictionary.service.TerminologyService;
import com.cocktailpick.back.tag.dto.TerminologyRequest;
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
}
