package com.cocktailpick.back.tag.domain;

import java.util.List;

import javax.persistence.Embeddable;
import javax.persistence.OneToMany;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class CocktailTags {
	@OneToMany(mappedBy = "cocktail")
	private List<CocktailTag> cocktailTags;
}
