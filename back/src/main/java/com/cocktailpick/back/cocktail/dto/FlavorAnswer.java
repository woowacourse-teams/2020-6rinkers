package com.cocktailpick.back.cocktail.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class FlavorAnswer {
	private UserPreferenceAnswer sweetAnswer;
	private UserPreferenceAnswer sourAnswer;
	private UserPreferenceAnswer bitterAnswer;
}