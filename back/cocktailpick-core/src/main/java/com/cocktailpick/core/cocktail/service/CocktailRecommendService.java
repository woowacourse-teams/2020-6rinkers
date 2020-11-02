package com.cocktailpick.core.cocktail.service;

import static java.util.stream.Collectors.*;

import java.util.List;
import java.util.function.Function;

import org.springframework.stereotype.Service;

import com.cocktailpick.core.cocktail.domain.Cocktail;
import com.cocktailpick.core.cocktail.domain.CocktailRepository;
import com.cocktailpick.core.cocktail.dto.CocktailDetailResponse;
import com.cocktailpick.core.cocktail.dto.RecommendRequest;
import com.cocktailpick.core.tag.domain.Tag;
import com.cocktailpick.core.tag.domain.TagRepository;
import com.cocktailpick.core.util.EntityMapper;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CocktailRecommendService {
	private final CocktailRepository cocktailRepository;
	private final TagRepository tagRepository;
	private final FilteringAndScoringRecommendService filteringAndScoringRecommendService;

	public List<CocktailDetailResponse> recommend(RecommendRequest recommendRequest) {
		List<Cocktail> cocktails = cocktailRepository.findAll();

		EntityMapper<Long, Tag> entityMapper = tagRepository.findAll()
			.stream()
			.collect(collectingAndThen(toMap(Tag::getId, Function.identity()), EntityMapper::new));

		List<Cocktail> recommend = filteringAndScoringRecommendService.recommend(cocktails, entityMapper,
			recommendRequest);

		return CocktailDetailResponse.listOf(recommend);
	}
}
