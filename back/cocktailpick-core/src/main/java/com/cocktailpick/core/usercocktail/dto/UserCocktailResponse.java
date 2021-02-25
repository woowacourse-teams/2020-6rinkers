package com.cocktailpick.core.usercocktail.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.cocktailpick.core.usercocktail.domain.UserCocktail;
import com.cocktailpick.core.userrecipe.domain.UserRecipeItem;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Getter
public class UserCocktailResponse {
	private Long id;
	private String name;
	private String description;
	private List<UserRecipeItemResponse> userRecipeItemResponses;

	public static UserCocktailResponse of(UserCocktail userCocktail) {
		List<UserRecipeItem> userRecipeItems = userCocktail.getUserRecipe().getUserRecipe();
		List<UserRecipeItemResponse> userRecipeItemResponses = userRecipeItems.stream()
			.map(userRecipeItem -> UserRecipeItemResponse.builder()
				.ingredientId(userRecipeItem.getIngredient().getId())
				.ingredientAbv(userRecipeItem.getIngredient().getAbv())
				.ingredientColor(userRecipeItem.getIngredient().getColor())
				.ingredientName(userRecipeItem.getIngredient().getName())
				.quantity(userRecipeItem.getQuantity())
				.quantityUnit(userRecipeItem.getQuantityUnit().name())
				.build())
			.collect(Collectors.toList());

		return new UserCocktailResponse(userCocktail.getId(), userCocktail.getName(), userCocktail.getDescription(),
			userRecipeItemResponses);
	}
}
