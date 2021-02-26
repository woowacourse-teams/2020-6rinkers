package com.cocktailpick.core.cocktail.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cocktailpick.common.util.DailyDate;
import com.cocktailpick.common.util.NumberOfDaily;
import com.cocktailpick.core.cocktail.domain.Cocktail;
import com.cocktailpick.core.cocktail.domain.CocktailFindStrategyFactory;
import com.cocktailpick.core.cocktail.domain.CocktailRepository;
import com.cocktailpick.core.cocktail.domain.CocktailSearcher;
import com.cocktailpick.core.cocktail.dto.CocktailDetailResponse;
import com.cocktailpick.core.cocktail.dto.CocktailRequest;
import com.cocktailpick.core.cocktail.dto.CocktailResponse;
import com.cocktailpick.core.common.exceptions.EntityNotFoundException;
import com.cocktailpick.core.common.exceptions.ErrorCode;
import com.cocktailpick.core.recipe.domain.RecipeItem;
import com.cocktailpick.core.tag.domain.CocktailTag;
import com.cocktailpick.core.tag.domain.Tag;
import com.cocktailpick.core.tag.domain.TagRepository;
import com.cocktailpick.core.util.EntityMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
@Service
public class CocktailService {
	private final CocktailRepository cocktailRepository;
	private final TagRepository tagRepository;
	private final CocktailFindStrategyFactory cocktailFindStrategyFactory;

	@Cacheable(value = "cocktails")
	@Transactional(readOnly = true)
	public List<CocktailResponse> findAllCocktails() {
		return Collections.unmodifiableList(
			CocktailResponse.listOf(cocktailRepository.findAllWithCocktailTagsAndRecipe()));
	}

	@Transactional(readOnly = true)
	public List<CocktailResponse> findPageContainingWord(String contain, long id, int size) {
		Pageable pageRequest = PageRequest.of(0, size);

		List<Cocktail> cocktails = cocktailRepository.findByNameContainingAndIdGreaterThanWithCocktailTags(contain, id,
			pageRequest)
			.getContent();

		return Collections.unmodifiableList(CocktailResponse.listOf(cocktails));
	}

	@Transactional(readOnly = true)
	public List<CocktailResponse> findPageFilteredByTags(List<Long> tagIds, long id, int size) {
		List<Cocktail> persistCocktails = cocktailRepository.findByIdGreaterThanWithCocktailTags(id);

		List<Cocktail> cocktails = persistCocktails.stream()
			.filter(cocktail -> cocktail.containTagIds(tagIds))
			.limit(size)
			.collect(Collectors.toList());

		return CocktailResponse.listOf(cocktails);
	}

	@Cacheable(value = "cocktail", key = "#id")
	@Transactional(readOnly = true)
	public CocktailDetailResponse findCocktail(Long id) {
		Cocktail cocktail = findById(id);
		return CocktailDetailResponse.of(cocktail);
	}

	@Transactional
	public Long save(CocktailRequest cocktailRequest) {
		Cocktail cocktail = cocktailRequest.toCocktail();
		cocktailRepository.save(cocktail);

		List<RecipeItem> recipeItems = cocktailRequest.toRecipeItems();
		setCocktail(cocktail, recipeItems);

		List<Tag> tags = tagRepository.findByNameIn(cocktailRequest.getTag());
		associate(cocktail, tags);

		return cocktail.getId();
	}

	@CachePut(value = "cocktail", key = "#id")
	@Transactional
	public CocktailDetailResponse updateCocktail(Long id, CocktailRequest cocktailRequest) {
		Cocktail cocktail = findById(id);
		Cocktail requestCocktail = cocktailRequest.toCocktail();
		List<RecipeItem> recipeItems = cocktailRequest.toRecipeItems();
		List<Tag> tags = tagRepository.findByNameIn(cocktailRequest.getTag());

		Cocktail updatedCocktail = cocktail.update(requestCocktail, tags, recipeItems);
		return CocktailDetailResponse.of(updatedCocktail);
	}

	private Cocktail findById(Long id) {
		return cocktailRepository.findById(id)
			.orElseThrow(() -> new EntityNotFoundException(ErrorCode.COCKTAIL_NOT_FOUND));
	}

	@CacheEvict(value = "cocktail", key = "#id")
	@Transactional
	public void deleteCocktail(Long id) {
		cocktailRepository.deleteById(id);
	}

	@CacheEvict(value = "cocktails")
	@Transactional
	public void deleteAllCocktails() {
		cocktailRepository.deleteAll();
	}

	@Transactional
	public void saveAll(List<CocktailRequest> cocktailRequests) {
		List<Tag> allTags = tagRepository.findAll();
		EntityMapper<String, Tag> tagMapper = mapTagToName(allTags);

		List<Cocktail> cocktails = new ArrayList<>();
		for (CocktailRequest cocktailRequest : cocktailRequests) {
			Cocktail cocktail = cocktailRequest.toCocktail();

			List<RecipeItem> recipeItems = cocktailRequest.toRecipeItems();
			setCocktail(cocktail, recipeItems);

			List<String> tagNames = cocktailRequest.getTag();
			List<Tag> tags = getTagsByName(tagMapper, tagNames);

			associate(cocktail, tags);
			cocktails.add(cocktail);
		}
		cocktailRepository.saveAll(cocktails);
	}

	private EntityMapper<String, Tag> mapTagToName(List<Tag> allTags) {
		EntityMapper<String, Tag> tagMapper = new EntityMapper<>(new HashMap<>());
		for (Tag tag : allTags) {
			tagMapper.put(tag.getName(), tag);
		}
		return tagMapper;
	}

	private void setCocktail(Cocktail cocktail, List<RecipeItem> recipeItems) {
		for (RecipeItem recipeItem : recipeItems) {
			recipeItem.setCocktail(cocktail);
		}
	}

	private List<Tag> getTagsByName(EntityMapper<String, Tag> tagMapper, List<String> tagNames) {
		return tagNames.stream()
			.map(tagMapper::get)
			.collect(Collectors.toList());
	}

	private void associate(Cocktail cocktail, List<Tag> tags) {
		for (Tag tag : tags) {
			CocktailTag.associate(cocktail, tag);
		}
	}

	@Transactional(readOnly = true)
	public CocktailResponse findCocktailOfToday() {
		DailyDate dailyDate = DailyDate.of(new Date());
		CocktailSearcher cocktailSearcher = cocktailFindStrategyFactory.createCocktailSearcher(
			NumberOfDaily.generateBy(dailyDate));

		List<Cocktail> cocktails = cocktailRepository.findAllWithCocktailTagsAndRecipe();

		Cocktail cocktailOfToday = cocktailSearcher.findIn(cocktails);
		return CocktailResponse.of(cocktailOfToday);
	}

	@Transactional(readOnly = true)
	public List<CocktailResponse> findByNameContaining(String name) {
		List<Cocktail> cocktailsContainingName = cocktailRepository.findByNameContainingWithCocktailTags(name);
		return CocktailResponse.listOf(cocktailsContainingName);
	}
}
