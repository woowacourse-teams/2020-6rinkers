package com.cocktailpick.back.cocktail.dto;

import com.cocktailpick.back.cocktail.domain.Cocktail;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class CocktailResponse {
	private String name;
	private String imageUrl;

	public static CocktailResponse of(Cocktail cocktail) {
		return new CocktailResponse(cocktail.getName(), cocktail.getImageUrl());
	}
}
