package com.cocktailpick.core.terminology.domain;

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

	private final String typeName;

	TerminologyType(String typeName) {
		this.typeName = typeName;
	}

	public static TerminologyType of(String typeName) {
		return Arrays.stream(TerminologyType.values())
			.filter(terminologyType -> terminologyType.getTypeName().equals(typeName))
			.findFirst()
			.orElseThrow(() -> new InvalidValueException(ErrorCode.TERMINOLOGY_TYPE_NOT_FOUND));
	}
}
