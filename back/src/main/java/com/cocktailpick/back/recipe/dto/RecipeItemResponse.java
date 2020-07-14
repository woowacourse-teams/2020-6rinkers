package com.cocktailpick.back.recipe.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.cocktailpick.back.recipe.domain.Recipe;
import com.cocktailpick.back.recipe.domain.RecipeItem;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class RecipeItemResponse {
	private String ingredient;

	private double quantity;

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
