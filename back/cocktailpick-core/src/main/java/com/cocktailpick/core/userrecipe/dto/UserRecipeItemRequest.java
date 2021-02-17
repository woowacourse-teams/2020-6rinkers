package com.cocktailpick.core.userrecipe.dto;

import com.cocktailpick.core.ingredient.domain.Ingredient;
import com.cocktailpick.core.userrecipe.domain.QuantityUnit;
import com.cocktailpick.core.userrecipe.domain.UserRecipeItem;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class UserRecipeItemRequest {
    @NotNull
    private Long ingredientId;
    @NotNull
    private int quantity;
    @NotBlank
    private String quantityUnit;

    @Builder
    public UserRecipeItemRequest(@NotNull Long ingredientId, @NotNull int quantity, @NotBlank String quantityUnit) {
        this.ingredientId = ingredientId;
        this.quantity = quantity;
        this.quantityUnit = quantityUnit;
    }

    public UserRecipeItem toUserRecipeItem(Ingredient ingredient) {
        return UserRecipeItem.builder()
                .ingredient(ingredient)
                .quantity(quantity)
                .quantityUnit(QuantityUnit.valueOf(quantityUnit))
                .build();
    }
}
