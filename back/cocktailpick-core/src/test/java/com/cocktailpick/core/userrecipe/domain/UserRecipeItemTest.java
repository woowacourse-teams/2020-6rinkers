package com.cocktailpick.core.userrecipe.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.cocktailpick.core.ingredient.domain.Ingredient;
import com.cocktailpick.core.usercocktail.domain.UserCocktail;

class UserRecipeItemTest {
    @DisplayName("유저 레시피를 생성한다.")
    @Test
    void create() {
        Ingredient ingredient = Ingredient.builder()
            .id(1L)
            .name("소주")
            .color("#000000")
            .abv(17.8)
            .build();

        UserCocktail userCocktail = UserCocktail.builder()
            .id(1L)
            .name("막소사")
            .description("막걸리와 소주와 사이다")
            .memberId(1L)
            .build();

        UserRecipeItem userRecipeItem = UserRecipeItem.builder()
            .ingredient(ingredient)
            .userCocktail(userCocktail)
            .build();

        assertThat(userRecipeItem).isInstanceOf(UserRecipeItem.class);
    }
}