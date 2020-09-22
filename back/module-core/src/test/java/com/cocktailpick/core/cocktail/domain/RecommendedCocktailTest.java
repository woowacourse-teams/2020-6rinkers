package com.cocktailpick.core.cocktail.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

class RecommendedCocktailTest {
	private Cocktail cocktail;

	@BeforeEach
	void setUp() {
		cocktail = Cocktail.builder()
			.name("두강")
			.build();
	}

	@DisplayName("기본 점수를 가진 추천 칵테일을 생성한다.")
	@Test
	void of() {
		assertThat(RecommendedCocktail.of(cocktail)).isNotNull();
	}

	@DisplayName("사용자의 응답에 따라 추천 점수를 더하거나 뺀다.")
	@ParameterizedTest
	@EnumSource(UserPreferenceAnswer.class)
	void addScoreByAnswer(UserPreferenceAnswer userPreferenceAnswer) {
		RecommendedCocktail recommendedCocktail = RecommendedCocktail.of(cocktail);

		assertThat(recommendedCocktail.addScoreByAnswer(it -> true, userPreferenceAnswer))
			.extracting("score")
			.isEqualTo(userPreferenceAnswer.getScore());
	}

	@DisplayName("사용자가 비선호 하는 조건인지 확인한다.")
	@Test
	void isDislike() {
		RecommendedCocktail recommendedCocktail = RecommendedCocktail.of(cocktail);
		assertThat(recommendedCocktail.isDislike(it -> true, UserPreferenceAnswer.NO)).isTrue();
	}
}