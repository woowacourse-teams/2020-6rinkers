package com.cocktailpick.core.cocktail.domain;

import java.util.List;

import com.cocktailpick.common.exceptions.EntityNotFoundException;
import com.cocktailpick.common.exceptions.ErrorCode;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@ToString
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
public class CocktailSearcher {
	private final long seed;

	public Cocktail findIn(List<Cocktail> cocktails) {
		validate(cocktails);
		int index = getRandomIndex(cocktails.size());
		return cocktails.get(index);
	}

	private void validate(List<Cocktail> cocktails) {
		if (cocktails.isEmpty()) {
			throw new EntityNotFoundException(ErrorCode.COCKTAIL_NOT_FOUND);
		}
	}

	private int getRandomIndex(int length) {
		return (int)(seed % length);
	}
}
