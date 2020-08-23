package com.cocktailpick.back.favorite.domain;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.OneToMany;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Embeddable
public class Favorites {
	@OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST, orphanRemoval = true)
	private Set<Favorite> favorites = new HashSet<>();

	public static Favorites empty() {
		return new Favorites();
	}

	public void deleteFavorite(Long cocktailId) {
		favorites = favorites.stream()
			.filter(favorite -> !favorite.getCocktail().isSameWith(cocktailId))
			.collect(Collectors.toSet());
	}
}
