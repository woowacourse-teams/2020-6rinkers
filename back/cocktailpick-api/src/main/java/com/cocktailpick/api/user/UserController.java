package com.cocktailpick.api.user;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cocktailpick.api.security.CurrentUser;
import com.cocktailpick.core.cocktail.dto.CocktailResponse;
import com.cocktailpick.core.favorite.dto.FavoriteRequest;
import com.cocktailpick.core.user.domain.User;
import com.cocktailpick.core.user.dto.FavoriteCocktailIdsResponse;
import com.cocktailpick.core.user.dto.UserUpdateRequest;
import com.cocktailpick.core.user.service.UserService;
import lombok.RequiredArgsConstructor;

@RequestMapping("/api/user")
@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
@RequiredArgsConstructor
@RestController
public class UserController {
	private final UserService userService;

	@GetMapping("/me")
	public ResponseEntity<com.cocktailpick.common.user.dto.UserResponse> getCurrentUser(@CurrentUser User user) {
		return ResponseEntity.ok(com.cocktailpick.common.user.dto.UserResponse.of(user));
	}

	@PatchMapping("/me")
	public ResponseEntity<com.cocktailpick.common.user.dto.UserResponse> updateCurrentUser(@CurrentUser User user,
		@RequestBody @Valid UserUpdateRequest userRequest) {
		userService.updateUser(user, userRequest);
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/me")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<Void> deleteCurrentUser(@CurrentUser User user) {
		userService.deleteCurrentUser(user);

		return ResponseEntity.noContent().build();
	}

	@GetMapping("/me/favorites")
	public ResponseEntity<List<CocktailResponse>> findFavorites(@CurrentUser User user) {
		return ResponseEntity.ok(userService.findFavorites(user));
	}

	@GetMapping("me/favoriteIds")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<FavoriteCocktailIdsResponse> findFavoriteCocktailIds(@CurrentUser User user) {
		return ResponseEntity.ok(userService.findFavoriteCocktailIds(user));
	}

	@PostMapping("/me/favorites")
	public ResponseEntity<Void> addFavorite(@CurrentUser User user,
		@RequestBody @Valid FavoriteRequest favoriteRequest) {
		Long favoriteId = userService.addFavorite(user, favoriteRequest);
		return ResponseEntity.created(URI.create("/api/user/me/favorites/" + favoriteId))
			.build();
	}

	@DeleteMapping("/me/favorites")
	public ResponseEntity<Void> deleteFavorite(@CurrentUser User user, @RequestParam("cocktailId") Long cocktailId) {
		userService.deleteFavorite(user, cocktailId);

		return ResponseEntity.noContent().build();
	}
}
