package com.cocktailpick.core.tag.dto;

import javax.validation.constraints.NotBlank;

import com.cocktailpick.core.tag.domain.Tag;
import com.cocktailpick.core.tag.domain.TagType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class TagRequest {
	@NotBlank
	private String name;

	@NotBlank
	private String tagType;

	public Tag toTag() {
		return Tag.builder()
			.name(name)
			.tagType(TagType.of(tagType))
			.build();
	}
}