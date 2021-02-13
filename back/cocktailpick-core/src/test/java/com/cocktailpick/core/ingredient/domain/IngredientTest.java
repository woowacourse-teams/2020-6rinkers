package com.cocktailpick.core.ingredient.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class IngredientTest {
    @DisplayName("재료를 수정한다.")
    @Test
    void update() {
        Ingredient ingredient = Ingredient.builder()
            .id(1L)
            .name("소주")
            .color("#000000")
            .abv(17.8)
            .build();

        Ingredient updatingIngredient = Ingredient.builder()
            .name(ingredient.getName())
            .color(ingredient.getColor())
            .abv(16.5)
            .build();

        ingredient.update(updatingIngredient);

        assertThat(ingredient.getAbv()).isEqualTo(updatingIngredient.getAbv());
    }
}