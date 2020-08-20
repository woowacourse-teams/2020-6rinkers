package com.cocktailpick.back.cocktail.domain;

import static java.util.stream.Collectors.*;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.cocktailpick.back.cocktail.vo.UserPreferenceAnswer;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class RecommendedCocktails {
	private static final int DEFAULT_RECOMMEND_COUNT = 3;

	private List<RecommendedCocktail> recommendedCocktails;

	public static RecommendedCocktails of(List<Cocktail> cocktails) {
		return cocktails.stream()
			.map(RecommendedCocktail::of)
			.collect(collectingAndThen(toList(), RecommendedCocktails::new));
	}

	public RecommendedCocktails eliminate(CocktailMatchingStrategy cocktailMatchingStrategy) {
		return eliminate(cocktailMatchingStrategy, UserPreferenceAnswer.NO);
	}

	public RecommendedCocktails eliminate(CocktailMatchingStrategy cocktailMatchingStrategy,
		UserPreferenceAnswer userPreferenceAnswer) {
		return recommendedCocktails.stream()
			.filter(
				recommendedCocktail -> !recommendedCocktail.isDislike(cocktailMatchingStrategy, userPreferenceAnswer))
			.collect(collectingAndThen(toList(), RecommendedCocktails::new));
	}

	public RecommendedCocktails addScoreByAnswer(CocktailMatchingStrategy cocktailMatchingStrategy,
		UserPreferenceAnswer userPreferenceAnswer) {
		return recommendedCocktails.stream()
			.map(recommendedCocktail -> recommendedCocktail.addScoreByAnswer(cocktailMatchingStrategy,
				userPreferenceAnswer))
			.collect(collectingAndThen(toList(), RecommendedCocktails::new));
	}

	public List<Cocktail> getSortedCocktailsByScore() {
		return recommendedCocktails.stream()
			.sorted(Comparator.comparingInt(RecommendedCocktail::getScore).reversed())
			.map(RecommendedCocktail::getCocktail)
			.limit(DEFAULT_RECOMMEND_COUNT)
			.collect(Collectors.toList());
	}
}
