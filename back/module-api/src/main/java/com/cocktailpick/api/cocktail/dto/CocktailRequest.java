package com.cocktailpick.api.cocktail.dto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PositiveOrZero;

import org.hibernate.validator.constraints.URL;

import com.cocktailpick.core.cocktail.domain.Cocktail;
import com.cocktailpick.core.cocktail.domain.Flavor;
import com.cocktailpick.core.recipe.domain.RecipeItem;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class CocktailRequest {
	@NotBlank
	private String name;
	@NotBlank
	private String description;
	@NotBlank
	private String origin;
	@NotEmpty
	private List<@NotBlank String> liquor;
	@NotEmpty
	private List<@NotBlank String> liquorQuantity;
	private List<@NotBlank String> special;
	private List<@NotBlank String> specialQuantity;
	private boolean sweet;
	private boolean sour;
	private boolean bitter;
	@PositiveOrZero
	private double abv;
	@URL
	private String imageUrl;
	@NotEmpty
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