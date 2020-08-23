package com.cocktailpick.back.cocktail.controller;

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

import com.cocktailpick.back.cocktail.dto.CocktailDetailResponse;
import com.cocktailpick.back.cocktail.dto.CocktailRequest;
import com.cocktailpick.back.cocktail.dto.CocktailResponse;
import com.cocktailpick.back.cocktail.dto.RecommendRequest;
import com.cocktailpick.back.cocktail.service.CocktailRecommendService;
import com.cocktailpick.back.cocktail.service.CocktailService;
import com.cocktailpick.back.common.exceptions.ResourceNotFoundException;
import com.cocktailpick.back.security.CurrentUser;
import com.cocktailpick.back.security.UserPrincipal;
import com.cocktailpick.back.user.domain.User;
import com.cocktailpick.back.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;

@CrossOrigin("*")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/cocktails")
public class CocktailController {
	private final CocktailService cocktailService;
	private final CocktailRecommendService cocktailRecommendService;
	private final UserRepository userRepository;

	@GetMapping
	public ResponseEntity<List<CocktailResponse>> findCocktails(@CurrentUser UserPrincipal userPrincipal) {
		if (userPrincipal != null) {
			User user = userRepository.findById(userPrincipal.getId())
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", userPrincipal.getId()));

			return ResponseEntity.ok(cocktailService.findCocktailsWithFavorite(user.getFavorites()));
		}
		return ResponseEntity.ok(cocktailService.findAllCocktails());
	}

	@GetMapping("/contain-word")
	public ResponseEntity<List<CocktailResponse>> findPageContainingWord(@CurrentUser UserPrincipal userPrincipal,
		@RequestParam(defaultValue = "") String contain, @RequestParam long id, @RequestParam int size) {
		if (userPrincipal != null) {
			User user = userRepository.findById(userPrincipal.getId())
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", userPrincipal.getId()));
			List<CocktailResponse> cocktailResponses = cocktailService.findPagedCocktailsWithFavorite(contain, id, size,
				user.getFavorites());
			return ResponseEntity.ok(cocktailResponses);
		}
		return ResponseEntity.ok(cocktailService.findPagedCocktails(contain, id, size));
	}

	@GetMapping("/contain-tags")
	public ResponseEntity<List<CocktailResponse>> findPageFilteredByTags(
		@RequestParam(defaultValue = "") List<Long> tagIds, @RequestParam long id,
		@RequestParam int size) {
		List<CocktailResponse> cocktailResponses = cocktailService.findPageFilteredByTags(tagIds, id, size);
		return ResponseEntity.ok(cocktailResponses);
	}

	@GetMapping("/{id}")
	public ResponseEntity<CocktailDetailResponse> findCocktail(@CurrentUser UserPrincipal userPrincipal,
		@PathVariable Long id) {
		if (userPrincipal != null) {
			User user = userRepository.findById(userPrincipal.getId())
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", userPrincipal.getId()));
			return ResponseEntity.ok(cocktailService.findCocktailWithFavorite(id, user.getFavorites()));
		}

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

	@PostMapping("/upload/csv")
	public ResponseEntity<Void> addCocktailsByCsv(@RequestPart MultipartFile file) {
		cocktailService.saveAll(file);
		return ResponseEntity.created(URI.create("/api/cocktails")).build();
	}

	@PostMapping("/recommend")
	public ResponseEntity<List<CocktailDetailResponse>> recommend(@CurrentUser UserPrincipal userPrincipal,
		@RequestBody RecommendRequest recommendRequests) {
		if (userPrincipal != null) {
			User user = userRepository.findById(userPrincipal.getId())
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", userPrincipal.getId()));
			List<CocktailDetailResponse> cocktailDetailResponses = cocktailRecommendService.recommendWithFavorite(
				recommendRequests,
				user.getFavorites());
			return ResponseEntity.ok(cocktailDetailResponses);
		}

		List<CocktailDetailResponse> cocktailDetailResponses = cocktailRecommendService.recommend(recommendRequests);
		return ResponseEntity.ok(cocktailDetailResponses);
	}

	@GetMapping("/auto-complete")
	public ResponseEntity<List<CocktailResponse>> findByNameContaining(
		@RequestParam @NotBlank String contain) {
		List<CocktailResponse> cocktailResponses = cocktailService.findByNameContaining(contain);
		return ResponseEntity.ok(cocktailResponses);
	}
}
