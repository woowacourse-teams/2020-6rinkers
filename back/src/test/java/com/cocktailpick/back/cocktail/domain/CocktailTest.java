package com.cocktailpick.back.cocktail.domain;

import static org.assertj.core.api.Assertions.*;

import java.util.Arrays;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.cocktailpick.back.tag.domain.CocktailTag;
import com.cocktailpick.back.tag.domain.Tag;

class CocktailTest {

	@DisplayName("칵테일이 태그 목록을 전부 포함하는지 확인한다.")
	@Test
	void containTags() {
		Cocktail cocktail = new Cocktail();

		Tag zeroId = Tag.builder()
			.id(0L)
			.build();

		Tag oneId = Tag.builder()
			.id(1L)
			.build();

		CocktailTag.associate(cocktail, zeroId);
		CocktailTag.associate(cocktail, oneId);

		assertThat(cocktail.containTagIds(Arrays.asList(0L, 1L)));
	}

	@DisplayName("칵테일이 태그 목록을 전부 포함하지 않는 경우를 확인한다.")
	@Test
	void containTags_WhenCocktailNotContainTagIds() {
		Cocktail cocktail = new Cocktail();

		Tag zeroId = Tag.builder()
			.id(0L)
			.build();

		Tag oneId = Tag.builder()
			.id(1L)
			.build();

		CocktailTag.associate(cocktail, zeroId);
		CocktailTag.associate(cocktail, oneId);

		assertThat(cocktail.containTagIds(Arrays.asList(0L, 3L)));
	}
}