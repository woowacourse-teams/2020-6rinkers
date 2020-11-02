package com.cocktailpick.core.favorite.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.cocktailpick.core.cocktail.domain.Cocktail;
import com.cocktailpick.core.user.domain.User;

class FavoritesTest {

	@DisplayName("즐겨찾기를 삭제한다.")
	@Test
	void deleteFavorite() {
		User toney = User.builder()
			.id(1L)
			.name("toney")
			.build();

		Cocktail cocktail1 = Cocktail.builder()
			.id(1L).build();

		Cocktail cocktail2 = Cocktail.builder()
			.id(2L).build();

		Cocktail cocktail3 = Cocktail.builder()
			.id(3L).build();

		Favorite favorite1 = new Favorite(1L, toney, cocktail1);
		Favorite favorite2 = new Favorite(2L, toney, cocktail2);
		Favorite favorite3 = new Favorite(3L, toney, cocktail3);

		Favorites favorites = new Favorites();

		favorites.addFavorite(favorite1);
		favorites.addFavorite(favorite2);
		favorites.addFavorite(favorite3);

		favorites.deleteFavorite(favorite1.getCocktail().getId());

		assertThat(favorites.getFavorites().size()).isEqualTo(2);
	}
}