package com.cocktailpick.core.cocktail.vo;

import lombok.Getter;

@Getter
public enum UserPreferenceAnswer {
	YES(1), NO(-1), SOSO(0);

	private final int score;

	UserPreferenceAnswer(int score) {
		this.score = score;
	}

	public int addScore(int score) {
		return score + this.score;
	}
}
