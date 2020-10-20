package com.cocktailpick.back.cocktail.domain;

import java.util.List;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;

import com.cocktailpick.back.common.domain.BaseTimeEntity;
import com.cocktailpick.back.recipe.domain.Recipe;
import com.cocktailpick.back.recipe.domain.RecipeItem;
import com.cocktailpick.back.tag.domain.CocktailTag;
import com.cocktailpick.back.tag.domain.CocktailTags;
import com.cocktailpick.back.tag.domain.Tag;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Cocktail extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cocktail_sequence_gen")
	@SequenceGenerator(
		name = "cocktail_sequence_gen",
		sequenceName = "cocktail_sequence",
		allocationSize = 1
	)
	private Long id;

	private String name;

	private double abv;

	@Lob
	private String description;

	@Lob
	private String origin;

	@Lob
	private String imageUrl;

	@Embedded
	private CocktailTags cocktailTags = CocktailTags.empty();

	@Embedded
	private Flavor flavor;

	@Embedded
	private Recipe recipe = Recipe.empty();

	@Builder
	public Cocktail(Long id, String name, double abv, String description, String origin,
		String imageUrl, Flavor flavor) {
		this.id = id;
		this.name = name;
		this.abv = abv;
		this.description = description;
		this.origin = origin;
		this.imageUrl = imageUrl;
		this.flavor = flavor;
	}

	public void update(Cocktail requestCocktail, List<Tag> tags, List<RecipeItem> recipeItems) {
		updateCocktailTags(tags);
		updateRecipe(recipeItems);

		this.name = requestCocktail.name;
		this.abv = requestCocktail.abv;
		this.description = requestCocktail.description;
		this.origin = requestCocktail.origin;
		this.flavor = requestCocktail.flavor;
		this.imageUrl = requestCocktail.imageUrl;
	}

	private void updateCocktailTags(List<Tag> tags) {
		this.cocktailTags.clear();
		for (Tag tag : tags) {
			CocktailTag.associate(this, tag);
		}
	}

	private void updateRecipe(List<RecipeItem> recipeItems) {
		this.recipe.clear();
		for (RecipeItem recipeItem : recipeItems) {
			recipeItem.setCocktail(this);
		}
	}

	public void deleteCocktailTag(CocktailTag cocktailTag) {
		cocktailTags.deleteCocktailTag(cocktailTag);
	}

	public boolean containsTag(Tag tag) {
		return !this.notContainsTag(tag);
	}

	public boolean notContainsTag(Tag tag) {
		return cocktailTags.notContainsTag(tag);
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

	public boolean isAbvBetween(int max, int min) {
		return (abv >= min) && (abv <= max);
	}

	public boolean containTagIds(List<Long> tagIds) {
		return cocktailTags.containTagIds(tagIds);
	}

	public boolean isSameWith(Long cocktailId) {
		return id.equals(cocktailId);
	}
}
