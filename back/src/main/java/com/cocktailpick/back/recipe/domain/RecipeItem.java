package com.cocktailpick.back.recipe.domain;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.cocktailpick.back.cocktail.domain.Cocktail;
import com.cocktailpick.back.common.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class RecipeItem extends BaseEntity {
	private String ingredient;

	private String quantity;

	@ManyToOne
	@JoinColumn(name = "cocktail_id")
	private Cocktail cocktail;

	@Builder
	private RecipeItem(Cocktail cocktail, String ingredient, String quantity) {
		this.cocktail = cocktail;
		this.ingredient = ingredient;
		this.quantity = quantity;
	}

	public boolean isSameIngredientWith(RecipeItem recipeItem) {
		return this.ingredient.equals(recipeItem.ingredient);
	}

	public void setCocktail(Cocktail cocktail) {
		removeRecipeItemIfUpdate(cocktail);

		this.cocktail = cocktail;
		cocktail.getRecipe().addRecipeItem(this);
	}

	private void removeRecipeItemIfUpdate(Cocktail cocktail) {
		if (cocktail != null) {
			cocktail.getRecipe().removeRecipeItem(this);
		}
	}
}
