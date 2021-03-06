package com.cocktailpick.api.cocktail.controller;

import java.io.IOException;
import java.net.URI;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

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

import com.cocktailpick.api.csv.CocktailCsvReader;
import com.cocktailpick.api.csv.OpenCsvReader;
import com.cocktailpick.core.cocktail.dto.CocktailDetailResponse;
import com.cocktailpick.core.cocktail.dto.CocktailRequest;
import com.cocktailpick.core.cocktail.dto.CocktailResponse;
import com.cocktailpick.core.cocktail.dto.RecommendRequest;
import com.cocktailpick.core.cocktail.service.CocktailRecommendService;
import com.cocktailpick.core.cocktail.service.CocktailService;
import lombok.RequiredArgsConstructor;

@CrossOrigin("*")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/cocktails")
public class CocktailController {
	private final CocktailService cocktailService;
	private final CocktailRecommendService cocktailRecommendService;

	@GetMapping
	public ResponseEntity<List<CocktailResponse>> findCocktails() {
		return ResponseEntity.ok(cocktailService.findAllCocktails());
	}

	@GetMapping("/contain-word")
	public ResponseEntity<List<CocktailResponse>> findPageContainingWord(
		@RequestParam(defaultValue = "") String contain, @RequestParam long id, @RequestParam int size) {
		List<CocktailResponse> cocktailResponses = cocktailService.findPageContainingWord(contain, id, size);
		return ResponseEntity.ok(cocktailResponses);
	}

	@GetMapping("/contain-tags")
	public ResponseEntity<List<CocktailResponse>> findPageFilteredByTags(
		@RequestParam(defaultValue = "") List<Long> tagIds, @RequestParam long id, @RequestParam int size) {
		return ResponseEntity.ok(cocktailService.findPageFilteredByTags(tagIds, id, size));
	}

	@GetMapping("/{id}")
	public ResponseEntity<CocktailDetailResponse> findCocktail(
		@PathVariable Long id) {
		return ResponseEntity.ok(cocktailService.findCocktail(id));
	}

	@PostMapping
	public ResponseEntity<Void> addCocktail(@RequestBody @Valid CocktailRequest cocktailRequest) {
		Long saveId = cocktailService.save(cocktailRequest);
		return ResponseEntity.created(URI.create("/api/cocktails/" + saveId)).build();
	}

	@PutMapping("/{id}")
	public ResponseEntity<Void> updateCocktail(@PathVariable Long id,
		@RequestBody @Valid CocktailRequest cocktailRequest) {
		cocktailService.updateCocktail(id, cocktailRequest);
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteCocktail(@PathVariable Long id) {
		cocktailService.deleteCocktail(id);
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping
	public ResponseEntity<Void> deleteAllCocktails() {
		cocktailService.deleteAllCocktails();
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/today")
	public ResponseEntity<CocktailResponse> findCocktailOfToday() {
		return ResponseEntity.ok(cocktailService.findCocktailOfToday());
	}

	@PostMapping("/recommend")
	public ResponseEntity<List<CocktailDetailResponse>> recommend(
		@RequestBody RecommendRequest recommendRequests) {
		List<CocktailDetailResponse> cocktailDetailResponses = cocktailRecommendService
			.recommend(recommendRequests);
		return ResponseEntity.ok(cocktailDetailResponses);
	}

	@PostMapping("/upload/csv")
	public ResponseEntity<Void> addCocktailsByCsv(@RequestPart MultipartFile file) throws IOException {
		CocktailCsvReader cocktailCsvReader = new CocktailCsvReader(OpenCsvReader.from(file.getInputStream()));
		cocktailService.saveAll(cocktailCsvReader.getCocktailRequests());
		return ResponseEntity.created(URI.create("/api/cocktails")).build();
	}

	@GetMapping("/auto-complete")
	public ResponseEntity<List<CocktailResponse>> findByNameContaining(
		@RequestParam @NotBlank String contain) {
		List<CocktailResponse> cocktailResponses = cocktailService.findByNameContaining(contain);
		return ResponseEntity.ok(cocktailResponses);
	}
}
