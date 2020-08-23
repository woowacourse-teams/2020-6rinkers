package com.cocktailpick.back.cocktail.dto;

import java.util.ArrayList;
import java.util.List;

import com.cocktailpick.back.cocktail.domain.Cocktail;
import com.cocktailpick.back.favorite.domain.Favorites;
import com.cocktailpick.back.recipe.dto.RecipeItemResponse;
import com.cocktailpick.back.tag.dto.TagResponse;
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

	private boolean isFavorite;

	public static CocktailDetailResponse of(Cocktail cocktail, boolean isFavorite) {
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
			RecipeItemResponse.listOf(cocktail.getRecipe()),
			isFavorite
		);
	}

	public static List<CocktailDetailResponse> listOf(List<Cocktail> recommend, Favorites favorites) {
		List<CocktailDetailResponse> cocktailDetailResponses = new ArrayList<>();

		for (Cocktail cocktail : recommend) {
			if (favorites.isContainCocktail(cocktail)) {
				cocktailDetailResponses.add(CocktailDetailResponse.of(cocktail, true));
			} else {
				cocktailDetailResponses.add(CocktailDetailResponse.of(cocktail, false));
			}
		}

		return cocktailDetailResponses;
	}
}
