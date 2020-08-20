package com.cocktailpick.back.cocktail.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.cocktailpick.back.cocktail.domain.Cocktail;
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
		return recommend.stream()
			.map(CocktailDetailResponse::of)
			.collect(Collectors.toList());
	}
}
