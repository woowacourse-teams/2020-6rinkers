package com.cocktailpick.back.cocktail.domain;

import java.util.List;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@ToString
@EqualsAndHashCode
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
public class CocktailSearcher {
	private final long seed;

	public Cocktail findIn(List<Cocktail> cocktails) {
		int index = getRandomIndex(cocktails.size());
		return cocktails.get(index);
	}

	private int getRandomIndex(int length) {
		return (int)(seed % length);
	}
}
