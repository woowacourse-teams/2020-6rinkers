package com.cocktailpick.core.ingredient.dto;

import com.cocktailpick.core.ingredient.domain.Ingredient;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class IngredientResponse {
    private String title;
    private String color;
    private Double abv;

    @Builder
    public IngredientResponse(String title, String color, Double abv) {
        this.title = title;
        this.color = color;
        this.abv = abv;
    }

    public static IngredientResponse of(Ingredient ingredient){
        return IngredientResponse.builder()
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
