package com.cocktailpick.core.userrecipe.domain;

import com.cocktailpick.core.common.domain.BaseTimeEntity;
import com.cocktailpick.core.ingredient.domain.Ingredient;
import com.cocktailpick.core.usercocktail.domain.UserCocktail;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class UserRecipeItem extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_recipe_item_sequence_gen")
    @SequenceGenerator(
            name = "user_recipe_item_sequence_gen",
            sequenceName = "user_recipe_item_sequence"
    )
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ingredient_id")
    private Ingredient ingredient;

    @ManyToOne
    @JoinColumn(name = "user_cocktail_id")
    private UserCocktail userCocktail;

    @NotNull
    private int quantity;

    @Enumerated(EnumType.STRING)
    private QuantityUnit quantityUnit;

    @Builder
    public UserRecipeItem(Ingredient ingredient, UserCocktail userCocktail, @NotNull int quantity, QuantityUnit quantityUnit) {
        this.ingredient = ingredient;
        this.userCocktail = userCocktail;
        this.quantity = quantity;
        this.quantityUnit = quantityUnit;
    }

    public void setUserCocktail(UserCocktail userCocktail) {
        this.userCocktail = userCocktail;
        userCocktail.getUserRecipe().addUserRecipeItem(this);
    }
}
