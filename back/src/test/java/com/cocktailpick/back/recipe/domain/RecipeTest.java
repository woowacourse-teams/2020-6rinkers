package com.cocktailpick.back.recipe.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.cocktailpick.back.common.exceptions.InvalidValueException;

class RecipeTest {
	@DisplayName("빈 Recipe 객체를 생성한다.")
	@Test
	void empty() {
		Recipe actual = Recipe.empty();

		assertThat(actual).isInstanceOf(Recipe.class);
	}

	@DisplayName("Recipe에 RecipeItem을 추가한다.")
	@Test
	void addRecipeItem() {
		Recipe recipe = Recipe.empty();
		RecipeItem recipeItem = RecipeItem.builder()
			.ingredient("두강")
			.quantity("두ml")
			.build();

		recipe.addRecipeItem(recipeItem);

		assertThat(recipe.getRecipeItems()).hasSize(1);
	}

	@DisplayName("RecipeItem 중복되는 경우 예외를 발생시킨다.")
	@Test
	void addDuplicatedRecipeItem() {
		Recipe recipe = Recipe.empty();
		RecipeItem recipeItem = RecipeItem.builder()
			.ingredient("두강")
			.quantity("두ml")
			.build();

		recipe.addRecipeItem(recipeItem);
		assertThatThrownBy(() -> recipe.addRecipeItem(recipeItem))
			.isInstanceOf(InvalidValueException.class);
	}
}
