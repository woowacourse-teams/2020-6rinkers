package com.cocktailpick.back.cocktail.service;

import java.util.List;

import com.cocktailpick.back.cocktail.domain.Cocktail;

public interface CocktailFindStrategy {
	Cocktail find(List<Cocktail> cocktails);
}
