package com.cocktailpick.core.tag.domain;

import java.util.Arrays;

import com.cocktailpick.core.common.exceptions.ErrorCode;
import com.cocktailpick.core.common.exceptions.InvalidValueException;
import lombok.Getter;

@Getter
public enum TagType {
	ABV("ABV"),
	INGREDIENT("INGREDIENT"),
	FLAVOR("FLAVOR"),
	TEXTURE("TEXTURE"),
	CONCEPT("CONCEPT"),
	DISLIKE("DISLIKE");

	private final String tagType;

	TagType(String tagType) {
		this.tagType = tagType;
	}

	public static TagType of(String tagType) {
		return Arrays.stream(TagType.values())
			.filter(type -> type.tagType.equals(tagType))
			.findFirst()
			.orElseThrow(() -> new InvalidValueException(ErrorCode.TAG_TYPE_NOT_FOUND));
	}
}
