package com.cocktailpick.back.cocktail.dto;

import java.util.ArrayList;
import java.util.List;

import com.cocktailpick.back.cocktail.domain.Cocktail;
import com.cocktailpick.back.cocktail.domain.Flavor;
import com.cocktailpick.back.recipe.domain.RecipeItem;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class CocktailRequest {
	private String name;
	private String description;
	private String origin;
	private List<String> liquor;
	private List<String> liquorQuantity;
	private List<String> special;
	private List<String> specialQuantity;
	private boolean sweet;
	private boolean sour;
	private boolean bitter;
	private double abv;
	private String imageUrl;
	private List<String> tag;

	@Builder
	public CocktailRequest(String name, String description, String origin, List<String> liquor,
		List<String> liquorQuantity, List<String> special, List<String> specialQuantity, boolean sweet, boolean sour,
		boolean bitter, double abv, String imageUrl, List<String> tag) {
		this.name = name;
		this.description = description;
		this.origin = origin;
		this.liquor = liquor;
		this.liquorQuantity = liquorQuantity;
		this.special = special;
		this.specialQuantity = specialQuantity;
		this.sweet = sweet;
		this.sour = sour;
		this.bitter = bitter;
		this.abv = abv;
		this.imageUrl = imageUrl;
		this.tag = tag;
	}

	public Cocktail toCocktail() {
		return Cocktail.builder()
			.name(name)
			.abv(abv)
			.description(description)
			.origin(origin)
			.flavor(toFlavor())
			.imageUrl(imageUrl)
			.build();
	}

	public Flavor toFlavor() {
		return new Flavor(sweet, sour, bitter);
	}

	public List<RecipeItem> toRecipeItems() {
		List<RecipeItem> recipeItems = new ArrayList<>();
		for (int i = 0; i < liquor.size(); i++) {
			RecipeItem item = RecipeItem.builder()
				.ingredient(liquor.get(i))
				.quantity(liquorQuantity.get(i))
				.build();
			recipeItems.add(item);
		}
		for (int i = 0; i < special.size(); i++) {
			RecipeItem item = RecipeItem.builder()
				.ingredient(special.get(i))
				.quantity(specialQuantity.get(i))
				.build();
			recipeItems.add(item);
		}
		return recipeItems;
	}
}