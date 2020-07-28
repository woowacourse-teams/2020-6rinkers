package com.cocktailpick.back.recipe.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.OneToMany;

import com.cocktailpick.back.common.exceptions.ErrorCode;
import com.cocktailpick.back.common.exceptions.InvalidValueException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class Recipe {
	@OneToMany(mappedBy = "cocktail", cascade = CascadeType.PERSIST, orphanRemoval = true)
	private List<RecipeItem> recipe = new ArrayList<>();

	public static Recipe empty() {
		return new Recipe();
	}

	public List<RecipeItem> getRecipeItems() {
		return recipe;
	}

	public void removeRecipeItem(RecipeItem recipeItem) {
		recipe.remove(recipeItem);
	}

	public void addRecipeItem(RecipeItem recipeItem) {
		if (isContainRecipeItem(recipeItem)) {
			throw new InvalidValueException(ErrorCode.RECIPE_ITEM_DUPLICATED);
		}

		recipe.add(recipeItem);
	}

	private boolean isContainRecipeItem(RecipeItem recipeItem) {
		return recipe.stream()
			.anyMatch(item -> item.isSameIngredientWith(recipeItem));
	}
}
