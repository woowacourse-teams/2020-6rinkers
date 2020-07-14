package com.cocktailpick.back.recipe.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Embeddable;
import javax.persistence.OneToMany;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Recipe {
	@OneToMany(mappedBy = "cocktail")
	private List<RecipeItem> recipe = new ArrayList<>();
}
