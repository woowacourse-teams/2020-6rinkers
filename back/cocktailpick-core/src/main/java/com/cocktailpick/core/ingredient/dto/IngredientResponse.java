package com.cocktailpick.core.ingredient.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.cocktailpick.core.ingredient.domain.Ingredient;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class IngredientResponse {
    private Long id;
    private String title;
    private String color;
    private Double abv;

    @Builder
    public IngredientResponse(Long id, String title, String color, Double abv) {
        this.id = id;
        this.title = title;
        this.color = color;
        this.abv = abv;
    }

    public static IngredientResponse of(Ingredient ingredient) {
        return IngredientResponse.builder()
            .id(ingredient.getId())
            .title(ingredient.getTitle())
            .abv(ingredient.getAbv())
            .color(ingredient.getColor())
            .build();
    }

    public static List<IngredientResponse> listOf(List<Ingredient> ingredients) {
        return ingredients.stream()
            .map(IngredientResponse::of)
            .collect(Collectors.toList());
    }
}
