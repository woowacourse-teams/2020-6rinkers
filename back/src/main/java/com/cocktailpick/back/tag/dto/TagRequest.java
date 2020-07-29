package com.cocktailpick.back.tag.dto;

import javax.validation.constraints.NotBlank;

import com.cocktailpick.back.tag.domain.Tag;
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

	public static Tag toTag(TagRequest tagRequest) {
		return new Tag(tagRequest.getName());
	}
}
