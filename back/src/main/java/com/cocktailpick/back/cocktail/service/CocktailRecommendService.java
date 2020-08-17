package com.cocktailpick.back.cocktail.service;

import static java.util.stream.Collectors.*;

import java.util.List;
import java.util.function.Function;

import org.springframework.stereotype.Service;

import com.cocktailpick.back.cocktail.domain.Cocktail;
import com.cocktailpick.back.cocktail.dto.CocktailResponse;
import com.cocktailpick.back.cocktail.dto.RecommendRequest;
import com.cocktailpick.back.common.EntityMapper;
import com.cocktailpick.back.tag.domain.Tag;
import com.cocktailpick.back.tag.service.TagService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CocktailRecommendService {
	private final CocktailService cocktailService;
	private final TagService tagService;
	private final FilteringAndScoringRecommendService filteringAndScoringRecommendService;

	public List<CocktailResponse> recommend(RecommendRequest recommendRequest) {
		List<Cocktail> cocktails = cocktailService.findAll();
		EntityMapper<Long, Tag> entityMapper = tagService.findAll()
			.stream()
			.collect(collectingAndThen(toMap(Tag::getId, Function.identity()), EntityMapper::new));
		List<Cocktail> recommend = filteringAndScoringRecommendService.recommend(cocktails, entityMapper,
			recommendRequest);

		return CocktailResponse.listOf(recommend);
	}
}
