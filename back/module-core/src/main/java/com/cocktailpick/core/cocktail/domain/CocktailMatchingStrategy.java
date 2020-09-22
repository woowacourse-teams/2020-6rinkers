package com.cocktailpick.core.cocktail.domain;

@FunctionalInterface
public interface CocktailMatchingStrategy {
	boolean match(Cocktail cocktail);
}
