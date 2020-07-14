package com.cocktailpick.back.recipe.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RecipeTest {
	@DisplayName("빈 Recipe 객체를 생성한다.")
	@Test
	void empty() {
		Recipe actual = Recipe.empty();

		assertThat(actual).isInstanceOf(Recipe.class);
	}
}
