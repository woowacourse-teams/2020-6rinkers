package com.cocktailpick.back.favorite.domain;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import com.cocktailpick.back.cocktail.domain.Cocktail;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Embeddable
public class Favorites {
	@OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST, orphanRemoval = true, fetch = FetchType.EAGER)
	private Set<Favorite> favorites = new HashSet<>();

	public static Favorites empty() {
		return new Favorites();
	}

	public void addFavorite(Favorite favorite) {
		favorites.add(favorite);
	}

	public void deleteFavorite(Long cocktailId) {
		Set<Favorite> deletedFavorites = favorites.stream()
			.filter(favorite -> !favorite.getCocktail().isSameWith(cocktailId))
			.collect(Collectors.toSet());
		favorites.clear();
		favorites.addAll(deletedFavorites);
	}

	public boolean isContainCocktail(Cocktail cocktail) {
		return favorites.stream()
			.anyMatch(favorite -> favorite.isContainCocktail(cocktail));
	}

	public boolean isDuplicated(Favorite inputFavorite) {
		return favorites.stream()
			.anyMatch(favorite -> favorite.isContainCocktail(inputFavorite.getCocktail()));
	}
}
