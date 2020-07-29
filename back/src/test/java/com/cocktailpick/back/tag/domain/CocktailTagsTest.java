package com.cocktailpick.back.tag.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.cocktailpick.back.cocktail.domain.Cocktail;
import com.cocktailpick.back.cocktail.domain.Flavor;
import com.cocktailpick.back.common.exceptions.InvalidValueException;

class CocktailTagsTest {
	private Tag tag;

	private Flavor flavor;

	private Cocktail blueHawaii;

	@BeforeEach
	void setUp() {
		tag = new Tag("두강맛");

		flavor = Flavor.builder()
			.bitter(true)
			.sour(true)
			.sweet(false)
			.build();

		blueHawaii = Cocktail.builder()
			.abv(40)
			.description("두강 맛 칵테일")
			.flavor(flavor)
			.imageUrl("https://naver.com")
			.name("DOO")
			.origin("두원이는 강하다.")
			.build();
	}

	@DisplayName("빈 CocktailTags를 생성한다.")
	@Test
	void empty() {
		CocktailTags actual = CocktailTags.empty();

		assertThat(actual).isInstanceOf(CocktailTags.class);
	}

	@DisplayName("CocktailTags에 CocktailTag를 추가한다.")
	@Test
	void add() {
		CocktailTags cocktailTags = new CocktailTags();

		CocktailTag cocktailTag = new CocktailTag();

		cocktailTag.setTag(tag);
		cocktailTag.setCocktail(blueHawaii);

		cocktailTags.addCocktailTag(cocktailTag);

		assertThat(cocktailTags.getTags()).hasSize(1);
	}

	@DisplayName("중복된 CocktailTag 추가 시 예외를 발생시킨다.")
	@Test
	void addDuplicatedCocktailTag() {
		CocktailTags cocktailTags = new CocktailTags();

		CocktailTag cocktailTag = new CocktailTag();

		cocktailTag.setTag(tag);
		cocktailTag.setCocktail(blueHawaii);

		cocktailTags.addCocktailTag(cocktailTag);

		assertThatThrownBy(() -> cocktailTags.addCocktailTag(cocktailTag))
			.isInstanceOf(InvalidValueException.class);
	}
}
