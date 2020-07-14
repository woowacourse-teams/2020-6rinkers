package com.cocktailpick.back.tag.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Embeddable;
import javax.persistence.OneToMany;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class CocktailTags {
	@OneToMany(mappedBy = "cocktail")
	private List<CocktailTag> cocktailTags = new ArrayList<>();

	public static CocktailTags empty() {
		return new CocktailTags();
	}

	public List<Tag> getTags() {
		return cocktailTags.stream()
			.map(CocktailTag::getTag)
			.collect(Collectors.toList());
	}
}
