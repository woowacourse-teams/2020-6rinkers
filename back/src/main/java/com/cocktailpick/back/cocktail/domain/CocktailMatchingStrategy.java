package com.cocktailpick.back.cocktail.domain;

@FunctionalInterface
public interface CocktailMatchingStrategy {
	boolean match(Cocktail cocktail);
}
