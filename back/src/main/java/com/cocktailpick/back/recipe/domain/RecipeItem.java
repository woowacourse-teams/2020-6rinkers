package com.cocktailpick.back.recipe.domain;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.cocktailpick.back.cocktail.domain.Cocktail;
import com.cocktailpick.back.common.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class RecipeItem extends BaseEntity {
	@ManyToOne
	@JoinColumn(name = "cocktail_id")
	private Cocktail cocktail;

	private String ingredient;

	private double quantity;
}
