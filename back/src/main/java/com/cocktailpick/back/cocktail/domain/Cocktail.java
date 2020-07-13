package com.cocktailpick.back.cocktail.domain;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Lob;

import com.cocktailpick.back.common.BaseEntity;
import com.cocktailpick.back.recipe.domain.Recipe;
import com.cocktailpick.back.tag.domain.CocktailTags;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Cocktail extends BaseEntity {
	private String name;

	private double abv;

	@Lob
	private String description;

	@Lob
	private String origin;

	private String imageUrl;

	@Embedded
	private CocktailTags tags = CocktailTags.empty();

	@Embedded
	private Flavor flavor;

	@Embedded
	private Recipe recipe = Recipe.empty();

	@Builder
	public Cocktail(String name, double abv, String description, String origin, String imageUrl,
		CocktailTags tags, Flavor flavor, Recipe recipe) {
		this.name = name;
		this.abv = abv;
		this.description = description;
		this.origin = origin;
		this.imageUrl = imageUrl;
		this.tags = tags;
		this.flavor = flavor;
		this.recipe = recipe;
	}
}
