package com.cocktailpick.back.favorite.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class FavoriteRequest {
	@Positive
	@NotBlank
	private Long cocktailId;
}
