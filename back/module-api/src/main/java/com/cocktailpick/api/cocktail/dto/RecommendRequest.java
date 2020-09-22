package com.cocktailpick.api.cocktail.dto;

import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class RecommendRequest {
	private AbvAnswer abvAnswer;
	private List<TagPreferenceAnswer> moodAnswers;
	private FlavorAnswer flavorAnswer;
	private List<TagPreferenceAnswer> preferenceAnswers;
	private List<TagPreferenceAnswer> nonPreferenceAnswers;
}