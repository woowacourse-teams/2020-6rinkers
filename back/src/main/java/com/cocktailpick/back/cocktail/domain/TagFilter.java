package com.cocktailpick.back.cocktail.domain;

import java.util.List;
import java.util.stream.Collectors;

import com.cocktailpick.back.tag.domain.Tag;

public enum TagFilter {
	HIGH_ABV("도수가 높은"),
	SWEET("단맛"),
	SOUR("신맛"),
	BITTER("쓴맛"),
	SODA("탄산"),
	SPICY("매운맛"),
	COFFEE("커피"),
	CHOCOLATE("초코"),
	COCONUT("코코넛"),
	MILK("밀크");

	private final String name;

	TagFilter(String name) {
		this.name = name;
	}

	public static List<Cocktail> filter(List<Cocktail> cocktails, Tag tag, Boolean answer) {
		if (answer) {
			return cocktails;
		}
		return cocktails.stream()
			.filter(cocktail -> cocktail.isNotContainsTag(tag))
			.collect(Collectors.toList());
	}

	public String getName() {
		return name;
	}
}
