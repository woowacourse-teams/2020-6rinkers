package com.cocktailpick.back.cocktail.service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.cocktailpick.back.cocktail.domain.Cocktail;
import com.cocktailpick.back.cocktail.domain.CocktailRepository;
import com.cocktailpick.back.cocktail.dto.CocktailDetailResponse;
import com.cocktailpick.back.cocktail.dto.CocktailRequest;
import com.cocktailpick.back.cocktail.dto.CocktailResponse;
import com.cocktailpick.back.common.csv.OpenCsvReader;
import com.cocktailpick.back.recipe.domain.RecipeItem;
import com.cocktailpick.back.tag.domain.CocktailTag;
import com.cocktailpick.back.tag.domain.CocktailTags;
import com.cocktailpick.back.tag.domain.Tag;
import com.cocktailpick.back.tag.domain.TagRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CocktailService {
	private final CocktailRepository cocktailRepository;
	private final TagRepository tagRepository;

	@Transactional(readOnly = true)
	public List<CocktailResponse> findAllCocktails() {
		return Collections.unmodifiableList(cocktailRepository.findAll().stream()
			.map(CocktailResponse::of)
			.collect(Collectors.toList()));
	}

	@Transactional(readOnly = true)
	public CocktailDetailResponse findCocktail(Long id) {
		Cocktail cocktail = cocktailRepository.findById(id)
			.orElseThrow(RuntimeException::new);

		return CocktailDetailResponse.of(cocktail);
	}

	@Transactional
	public Long save(CocktailRequest cocktailRequest) {
		Cocktail cocktail = cocktailRequest.toCocktail();
		cocktailRepository.save(cocktail);

		List<RecipeItem> recipeItems = cocktailRequest.toRecipeItems();
		for (RecipeItem recipeItem : recipeItems) {
			recipeItem.setCocktail(cocktail);
		}

		List<Tag> tags = tagRepository.findByNameIn(cocktailRequest.getTag());
		for (Tag tag : tags) {
			CocktailTag.connect(cocktail, tag);
		}

		return cocktail.getId();
	}

	@Transactional
	public void updateCocktail(Long id, CocktailRequest cocktailRequest) {
		Cocktail cocktail = findById(id);
		Cocktail requestCocktail = cocktailRequest.toCocktail();

		List<Tag> tags = tagRepository.findByNameIn(cocktailRequest.getTag());
		CocktailTags cocktailTags = tags.stream()
			.map(tag -> CocktailTag.connect(cocktail, tag))
			.collect(
				Collectors.collectingAndThen(Collectors.toList(), CocktailTags::new));

		cocktail.update(requestCocktail, cocktailTags);
	}

	private Cocktail findById(Long id) {
		return cocktailRepository.findById(id)
			.orElseThrow(RuntimeException::new);
	}

	@Transactional
	public void deleteCocktail(Long id) {
		cocktailRepository.deleteById(id);
	}

	@Transactional
	public void saveAll(MultipartFile file) {
		CocktailCsvReader cocktailCsvReader = new CocktailCsvReader(OpenCsvReader.from(file));
		List<CocktailRequest> cocktailRequests = cocktailCsvReader.getCocktailRequests();

		for (CocktailRequest cocktailRequest : cocktailRequests) {
			Cocktail cocktail = cocktailRequest.toCocktail();
			cocktailRepository.save(cocktail);

			List<RecipeItem> recipeItems = cocktailRequest.toRecipeItems();
			for (RecipeItem recipeItem : recipeItems) {
				recipeItem.setCocktail(cocktail);
			}

			List<String> tagNames = cocktailRequest.getTag();
			List<Tag> tags = tagRepository.findByNameIn(tagNames);
			for (Tag tag : tags) {
				CocktailTag.connect(cocktail, tag);
			}
		}
	}
}
