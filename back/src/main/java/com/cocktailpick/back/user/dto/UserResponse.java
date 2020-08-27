package com.cocktailpick.back.user.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.cocktailpick.back.cocktail.dto.CocktailResponse;
import com.cocktailpick.back.user.domain.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class UserResponse {
	private Long id;

	private String name;

	private String email;

	private String imageUrl;

	private Boolean emailVerified;

	private String provider;

	private String providerId;

	private String role;

	private List<CocktailResponse> myFavorites;

	public static UserResponse of(User user) {
		return new UserResponse(user.getId(), user.getName(), user.getEmail(), user.getImageUrl(),
			user.getEmailVerified(), user.getProvider().name(), user.getProviderId(), user.roleName(),
			user.getFavorites()
				.getFavorites()
				.stream()
				.map(favorite -> CocktailResponse.of(favorite.getCocktail(), true))
				.collect(Collectors.toList()));
	}
}
