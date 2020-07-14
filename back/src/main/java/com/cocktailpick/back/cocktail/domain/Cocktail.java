package com.cocktailpick.back.cocktail.domain;

import java.util.List;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Lob;

import com.cocktailpick.back.common.BaseEntity;
import com.cocktailpick.back.recipe.domain.Recipe;
import com.cocktailpick.back.tag.domain.CocktailTags;
import com.cocktailpick.back.tag.domain.Tag;
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
	private CocktailTags cocktailTags = CocktailTags.empty();

	@Embedded
	private Flavor flavor;

	@Embedded
	private Recipe recipe = Recipe.empty();

	@Builder
	public Cocktail(String name, double abv, String description, String origin,
		String imageUrl, Flavor flavor) {
		this.name = name;
		this.abv = abv;
		this.description = description;
		this.origin = origin;
		this.imageUrl = imageUrl;
		this.flavor = flavor;
	}

	public List<Tag> getTags() {
		return cocktailTags.getTags();
	}

	public boolean isSweet() {
		return flavor.isSweet();
	}

	public boolean isSour() {
		return flavor.isSour();
	}

	public boolean isBitter() {
		return flavor.isBitter();
	}
}
