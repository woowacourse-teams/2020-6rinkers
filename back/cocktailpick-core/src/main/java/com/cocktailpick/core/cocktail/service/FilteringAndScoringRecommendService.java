package com.cocktailpick.core.cocktail.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cocktailpick.core.cocktail.domain.Cocktail;
import com.cocktailpick.core.cocktail.domain.RecommendedCocktails;
import com.cocktailpick.core.cocktail.dto.AbvAnswer;
import com.cocktailpick.core.cocktail.dto.FlavorAnswer;
import com.cocktailpick.core.cocktail.dto.RecommendRequest;
import com.cocktailpick.core.cocktail.dto.TagPreferenceAnswer;
import com.cocktailpick.core.tag.domain.Tag;
import com.cocktailpick.core.util.EntityMapper;

@Service
public class FilteringAndScoringRecommendService {
	public List<Cocktail> recommend(List<Cocktail> cocktails, EntityMapper<Long, Tag> entityMapper,
		RecommendRequest recommendRequest) {
		RecommendedCocktails recommendedCocktails = filterByAbv(RecommendedCocktails.of(cocktails),
			recommendRequest.getAbvAnswer());
		recommendedCocktails = filterByDislike(recommendedCocktails, recommendRequest.getNonPreferenceAnswers(),
			entityMapper);
		recommendedCocktails = addScoreByFlavor(recommendedCocktails, recommendRequest.getFlavorAnswer());
		recommendedCocktails = addScoreByPreference(recommendedCocktails, recommendRequest.getMoodAnswers(),
			entityMapper);
		recommendedCocktails = addScoreByPreference(recommendedCocktails, recommendRequest.getPreferenceAnswers(),
			entityMapper);
		return recommendedCocktails.getSortedCocktailsByScore();
	}

	private RecommendedCocktails filterByAbv(RecommendedCocktails recommendedCocktails, AbvAnswer abvAnswer) {
		return recommendedCocktails.eliminate(
			cocktail -> !cocktail.isAbvBetween(abvAnswer.getMax(), abvAnswer.getMin()));
	}

	private RecommendedCocktails filterByDislike(RecommendedCocktails recommendedCocktails,
		List<TagPreferenceAnswer> nonPreferenceAnswer, EntityMapper<Long, Tag> entityMapper) {
		for (TagPreferenceAnswer tagPreferenceAnswer : nonPreferenceAnswer) {
			recommendedCocktails = recommendedCocktails.eliminate(
				cocktail -> cocktail.containsTag(entityMapper.get(tagPreferenceAnswer.getTagId())),
				tagPreferenceAnswer.getUserPreferenceAnswer());
		}
		return recommendedCocktails;
	}

	private RecommendedCocktails addScoreByFlavor(RecommendedCocktails recommendedCocktails,
		FlavorAnswer flavorAnswer) {
		recommendedCocktails = recommendedCocktails.addScoreByAnswer(Cocktail::isSweet,
			flavorAnswer.getSweetAnswer())
			.addScoreByAnswer(Cocktail::isBitter, flavorAnswer.getBitterAnswer())
			.addScoreByAnswer(Cocktail::isSour, flavorAnswer.getSourAnswer());
		return recommendedCocktails;
	}

	private RecommendedCocktails addScoreByPreference(RecommendedCocktails recommendedCocktails,
		List<TagPreferenceAnswer> preferenceAnswers, EntityMapper<Long, Tag> entityMapper) {
		for (TagPreferenceAnswer preferenceAnswer : preferenceAnswers) {
			recommendedCocktails = recommendedCocktails.addScoreByAnswer(
				cocktail -> cocktail.containsTag(entityMapper.get(preferenceAnswer.getTagId())),
				preferenceAnswer.getUserPreferenceAnswer());
		}
		return recommendedCocktails;
	}
}
