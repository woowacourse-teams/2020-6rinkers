package com.cocktailpick.core.usercocktail.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UserRecipeItemResponse {
	private Long ingredientId;
	private String ingredientName;
	private String ingredientColor;
	private Double ingredientAbv;
	private Double quantity;
	private String quantityUnit;

	@Builder
	public UserRecipeItemResponse(Long ingredientId, String ingredientName, String ingredientColor,
		Double ingredientAbv, Double quantity, String quantityUnit) {
		this.ingredientId = ingredientId;
		this.ingredientName = ingredientName;
		this.ingredientColor = ingredientColor;
		this.ingredientAbv = ingredientAbv;
		this.quantity = quantity;
		this.quantityUnit = quantityUnit;
	}
}
