package com.cocktailpick.core.ingredient.dto;

import com.cocktailpick.core.ingredient.domain.Ingredient;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class IngredientRequest {
    @NotBlank
    private String name;
    @NotBlank
    private String color;
    @NotNull
    private Double abv;

    @Builder
    public IngredientRequest(@NotBlank String name, @NotBlank String color, @NotNull Double abv) {
        this.name = name;
        this.color = color;
        this.abv = abv;
    }

    public Ingredient toIngredient() {
        return Ingredient.builder()
                .name(name)
                .color(color)
                .abv(abv)
                .build();
    }
}
