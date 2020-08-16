package com.cocktailpick.back.tag.domain;

import java.util.Arrays;

import lombok.Getter;

@Getter
public enum TagType {
	ABV("도수"),
	INGREDIENT("재료"),
	FLAVOR("맛"),
	TEXTURE("식감"),
	CONCEPT("컨셉"),
	DISLIKE("꺼릴만한 것");

	private final String tagType;

	TagType(String tagType) {
		this.tagType = tagType;
	}

	public static TagType of(String tagType) {
		return Arrays.stream(TagType.values())
			.filter(type -> type.tagType.equals(tagType))
			.findFirst()
			.orElseThrow(IllegalArgumentException::new);
	}
}
