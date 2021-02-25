package com.cocktailpick.core.usercocktail.dto;

import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Getter
public class UserCocktailResponses {
	private List<UserCocktailResponse> userCocktailResponses;
}
