package com.cocktailpick.core.recipe.domain;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.cocktailpick.core.cocktail.domain.Cocktail;
import com.cocktailpick.core.cocktail.domain.Flavor;

class RecipeItemTest {
	private RecipeItem recipeItem;

	@BeforeEach
	void setUp() {
		recipeItem = RecipeItem.builder()
			.ingredient("두강")
			.quantity("10개")
			.build();
	}

	@DisplayName("item이 같은 Ingredient를 가지는 지 확인한다.")
	@Test
	void isSameIngredientWith() {
		RecipeItem comparedRecipeItem = RecipeItem.builder()
			.ingredient("두강")
			.quantity("20개")
			.build();

		assertThat(recipeItem.isSameIngredientWith(comparedRecipeItem)).isTrue();
	}

	@DisplayName("칵테일을 지정했을 때 편의 메서드가 정상적으로 동작하는 지 확인한다.")
	@Test
	void setCocktail() {
		Flavor flavor = Flavor.builder()
			.bitter(true)
			.sour(true)
			.sweet(false)
			.build();
		Cocktail blueHawaii = Cocktail.builder()
			.abv(40)
			.description("두강 맛 칵테일")
			.flavor(flavor)
			.imageUrl("https://naver.com")
			.name("DOO")
			.origin("두원이는 강하다.")
			.build();
		Cocktail redHawaii = Cocktail.builder()
			.abv(40)
			.description("토니 맛 칵테일")
			.flavor(flavor)
			.imageUrl("https://toni.com")
			.name("TOO")
			.origin("토니는 강하다.")
			.build();

		recipeItem.setCocktail(blueHawaii);
		recipeItem.setCocktail(redHawaii);

		assertAll(
			() -> assertThat(recipeItem.getCocktail()).isEqualTo(redHawaii),
			() -> assertThat(blueHawaii.getRecipe().getRecipeItems()).isEmpty(),
			() -> assertThat(redHawaii.getRecipe().getRecipeItems()).hasSize(1)
		);
	}
}