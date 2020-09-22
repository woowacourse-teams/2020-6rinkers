package com.cocktailpick.api.cocktail.dto;

import java.util.ArrayList;
import java.util.List;

import com.cocktailpick.api.recipe.dto.RecipeItemResponse;
import com.cocktailpick.api.tag.dto.TagResponse;
import com.cocktailpick.core.cocktail.domain.Cocktail;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.With;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class CocktailDetailResponse {
	@With(value = AccessLevel.PUBLIC)
	private Long id;

	private String name;

	private double abv;

	private String description;

	private String origin;

	private String imageUrl;

	private List<TagResponse> tags;

	private boolean sweet;

	private boolean sour;

	private boolean bitter;

	private List<RecipeItemResponse> recipe;

	public static CocktailDetailResponse of(Cocktail cocktail) {
		return new CocktailDetailResponse(
			cocktail.getId(),
			cocktail.getName(),
			cocktail.getAbv(),
			cocktail.getDescription(),
			cocktail.getOrigin(),
			cocktail.getImageUrl(),
			TagResponse.listOf(cocktail.getTags()),
			cocktail.isSweet(),
			cocktail.isSour(),
			cocktail.isBitter(),
			RecipeItemResponse.listOf(cocktail.getRecipe())
		);
	}

	public static List<CocktailDetailResponse> listOf(List<Cocktail> recommend) {
		List<CocktailDetailResponse> cocktailDetailResponses = new ArrayList<>();

		for (Cocktail cocktail : recommend) {
			cocktailDetailResponses.add(of(cocktail));
		}

		return cocktailDetailResponses;
	}
}
