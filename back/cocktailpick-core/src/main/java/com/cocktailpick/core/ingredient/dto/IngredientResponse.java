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
    private String name;
    private String color;
    private Double abv;

    @Builder
    public IngredientResponse(Long id, String name, String color, Double abv) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.abv = abv;
    }

    public static IngredientResponse of(Ingredient ingredient) {
        return IngredientResponse.builder()
            .id(ingredient.getId())
            .name(ingredient.getName())
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
