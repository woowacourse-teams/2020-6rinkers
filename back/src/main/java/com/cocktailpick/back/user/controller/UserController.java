package com.cocktailpick.back.user.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cocktailpick.back.cocktail.dto.CocktailResponse;
import com.cocktailpick.back.favorite.dto.FavoriteRequest;
import com.cocktailpick.back.security.CurrentUser;
import com.cocktailpick.back.user.domain.User;
import com.cocktailpick.back.user.dto.UserResponse;
import com.cocktailpick.back.user.dto.UserUpdateRequest;
import com.cocktailpick.back.user.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequestMapping("/api/user")
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
@RestController
public class UserController {
	private final UserService userService;

	@GetMapping("/me")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<UserResponse> getCurrentUser(@CurrentUser User user) {
		return ResponseEntity.ok(UserResponse.of(user));
	}

	@PutMapping("/me")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<UserResponse> updateCurrentUser(@CurrentUser User user,
		@RequestBody @Valid UserUpdateRequest userRequest) {
		userService.updateUser(user, userRequest);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/me/favorites")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<List<CocktailResponse>> findFavorites(@CurrentUser User user) {
		return ResponseEntity.ok(userService.findFavorites(user));
	}

	@PostMapping("/me/favorites")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<Void> addFavorite(@CurrentUser User user,
		@RequestBody @Valid FavoriteRequest favoriteRequest) {
		Long favoriteId = userService.addFavorite(user, favoriteRequest);
		return ResponseEntity.created(URI.create("/api/user/me/favorites/" + favoriteId))
			.build();
	}

	@DeleteMapping("/me/favorites/{cocktailId}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<Void> deleteFavorite(@CurrentUser User user, @PathVariable Long cocktailId) {
		userService.deleteFavorite(user, cocktailId);

		return ResponseEntity.noContent().build();
	}
}
