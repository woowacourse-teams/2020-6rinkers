package com.cocktailpick.back.cocktail.dto;

import java.util.ArrayList;
import java.util.List;

import com.cocktailpick.back.cocktail.domain.Cocktail;
import com.cocktailpick.back.tag.dto.TagResponse;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.With;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class CocktailResponse {
	@With(value = AccessLevel.PUBLIC)
	private Long id;

	private String name;

	private String imageUrl;

	private List<TagResponse> tags;

	public static CocktailResponse of(Cocktail cocktail) {
		return new CocktailResponse(cocktail.getId(), cocktail.getName(), cocktail.getImageUrl(),
			TagResponse.listOf(cocktail.getTags()));
	}

	public static List<CocktailResponse> listOf(List<Cocktail> cocktails) {
		List<CocktailResponse> cocktailResponses = new ArrayList<>();

		for (Cocktail cocktail : cocktails) {
			cocktailResponses.add(of(cocktail));
		}

		return cocktailResponses;
	}
}
