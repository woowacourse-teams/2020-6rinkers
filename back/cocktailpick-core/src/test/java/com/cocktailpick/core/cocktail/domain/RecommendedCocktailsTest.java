package com.cocktailpick.core.cocktail.domain;

import static org.assertj.core.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.cocktailpick.core.cocktail.vo.UserPreferenceAnswer;

class RecommendedCocktailsTest {
	private List<Cocktail> cocktails;

	private Cocktail cocktail1;

	private Cocktail cocktail2;

	private Cocktail cocktail3;

	private Cocktail cocktail4;

	@BeforeEach
	void setUp() {
		cocktail1 = Cocktail.builder()
			.name("a")
			.build();
		cocktail2 = Cocktail.builder()
			.name("b")
			.build();
		cocktail3 = Cocktail.builder()
			.name("c")
			.build();
		cocktail4 = Cocktail.builder()
			.name("d")
			.build();
		cocktails = Arrays.asList(cocktail1, cocktail2, cocktail3, cocktail4);
	}

	@DisplayName("추천 칵테일 목록을 다루는 객체를 생성한다.")
	@Test
	void of() {
		assertThat(RecommendedCocktails.of(cocktails)).isNotNull();
	}

	@DisplayName("조건에 해당하는 칵테일을 추천 목록에서 없앤다.")
	@Test
	void eliminate() {
		RecommendedCocktails recommendedCocktails = RecommendedCocktails.of(cocktails);
		RecommendedCocktails eliminate = recommendedCocktails.eliminate(
			cocktail -> cocktail.getName().equals(cocktail1.getName()));

		assertThat(eliminate).extracting("recommendedCocktails").asInstanceOf(InstanceOfAssertFactories.LIST)
			.extracting("com/cocktailpick/core/cocktail")
			.containsExactlyInAnyOrder(cocktail2, cocktail3, cocktail4);
	}

	@DisplayName("조건에 해당하는 칵테일을 사용자가 비선호 하는 경우 추천 목록에서 없앤다.")
	@Test
	void eliminateWithUserAnswer() {
		RecommendedCocktails recommendedCocktails = RecommendedCocktails.of(cocktails);
		RecommendedCocktails eliminate = recommendedCocktails.eliminate(
			cocktail -> cocktail.getName().equals(cocktail1.getName()), UserPreferenceAnswer.NO);

		assertThat(eliminate).extracting("recommendedCocktails").asInstanceOf(InstanceOfAssertFactories.LIST)
			.extracting("com/cocktailpick/core/cocktail")
			.containsExactlyInAnyOrder(cocktail2, cocktail3, cocktail4);
	}

	@DisplayName("조건에 해당하는 칵테일을 사용자의 응답에 따라 추천 점수를 더한다.")
	@Test
	void addScoreByAnswer() {
		RecommendedCocktails recommendedCocktails = RecommendedCocktails.of(cocktails);
		recommendedCocktails = recommendedCocktails.addScoreByAnswer(
			cocktail -> cocktail.getName().equals(cocktail1.getName()) || cocktail.getName()
				.equals(cocktail2.getName()), UserPreferenceAnswer.YES);

		assertThat(recommendedCocktails).extracting("recommendedCocktails").asInstanceOf(InstanceOfAssertFactories.LIST)
			.extracting("score")
			.containsExactly(1, 1, 0, 0);
	}

	@DisplayName("추천 결과를 추천 점수 순으로 정렬해 반환한다.")
	@Test
	void getSortedCocktailsByScore() {
		RecommendedCocktails recommendedCocktails = RecommendedCocktails.of(cocktails);
		recommendedCocktails = recommendedCocktails.addScoreByAnswer(
			cocktail -> cocktail.getName().equals(cocktail1.getName()) || cocktail.getName()
				.equals(cocktail4.getName()), UserPreferenceAnswer.YES);
		recommendedCocktails = recommendedCocktails.eliminate(
			cocktail -> cocktail == cocktail1 || cocktail == cocktail3);

		assertThat(recommendedCocktails.getSortedCocktailsByScore())
			.containsExactly(cocktail4, cocktail2);
	}
}