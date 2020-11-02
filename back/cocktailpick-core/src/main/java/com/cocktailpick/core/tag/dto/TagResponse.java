package com.cocktailpick.core.tag.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.cocktailpick.core.tag.domain.Tag;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@EqualsAndHashCode
@ToString
public class TagResponse {
	private Long tagId;

	private String name;

	private String tagType;

	public static List<TagResponse> listOf(List<Tag> tags) {
		return tags.stream()
			.map(TagResponse::of)
			.collect(Collectors.toList());
	}

	private static TagResponse of(Tag tag) {
		return new TagResponse(tag.getId(), tag.getName(), tag.getTagType());
	}
}
