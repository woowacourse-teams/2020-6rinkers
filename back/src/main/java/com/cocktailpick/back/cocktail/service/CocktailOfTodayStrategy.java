package com.cocktailpick.back.cocktail.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

import com.cocktailpick.back.cocktail.domain.Cocktail;

public class CocktailOfTodayStrategy implements CocktailFindStrategy {
	@Override
	public Cocktail findIn(List<Cocktail> cocktails) {
		return cocktails.get(getIndex(cocktails.size()));
	}

	private int getIndex(int length) {
		Random random = new Random(createSeed());
		return (int)(random.nextDouble() * length);
	}

	private long createSeed() {
		try {
			Date date = new Date();
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
			String format = simpleDateFormat.format(date);

			return simpleDateFormat.parse(format).getTime();
		} catch (ParseException e) {
			throw new RuntimeException();
		}
	}
}
