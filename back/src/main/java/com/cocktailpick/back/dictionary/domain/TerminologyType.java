package com.cocktailpick.back.dictionary.domain;

import java.util.Arrays;

import com.cocktailpick.back.common.exceptions.ErrorCode;
import com.cocktailpick.back.common.exceptions.InvalidValueException;
import lombok.Getter;

@Getter
public enum TerminologyType {
	COCKTAIL("칵테일"),
	ALCOHOL("술"),
	METHOD("제조법"),
	SHAPE("형태");

	private String koreanName;

	TerminologyType(String koreanName) {
		this.koreanName = koreanName;
	}

	public static TerminologyType of(String koreanName) {
		return Arrays.stream(TerminologyType.values())
			.filter(terminologyType -> terminologyType.getKoreanName().equals(koreanName))
			.findFirst()
			.orElseThrow(() -> new InvalidValueException(ErrorCode.TERMINOLOGY_TYPE_NOT_FOUND));
	}
}
