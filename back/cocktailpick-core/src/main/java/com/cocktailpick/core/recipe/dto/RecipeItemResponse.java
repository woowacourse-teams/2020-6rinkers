package com.cocktailpick.core.recipe.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.cocktailpick.core.recipe.domain.Recipe;
import com.cocktailpick.core.recipe.domain.RecipeItem;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class RecipeItemResponse {
	private String ingredient;

	private String quantity;

	private static RecipeItemResponse of(RecipeItem recipeItem) {
		return new RecipeItemResponse(recipeItem.getIngredient(),
			recipeItem.getQuantity());
	}

	public static List<RecipeItemResponse> listOf(Recipe recipe) {
		return recipe.getRecipeItems().stream()
			.map(RecipeItemResponse::of)
			.collect(Collectors.toList());
	}
}
