package com.cocktailpick.back.cocktail.service;

import java.util.List;

import com.cocktailpick.back.cocktail.domain.Cocktail;
import com.cocktailpick.back.cocktail.dto.CocktailResponse;
import com.cocktailpick.back.cocktail.dto.RecommendRequest;
import com.cocktailpick.back.common.EntityMapper;
import com.cocktailpick.back.tag.domain.Tag;

public class FilteringAndScoringRecommendService {
	public List<CocktailResponse> recommend(List<Cocktail> cocktails, EntityMapper<Long, Tag> entityMapper,
		RecommendRequest recommendRequest) {
		return null;
	}
}
