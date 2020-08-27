package com.cocktailpick.back.tag.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import com.cocktailpick.back.common.exceptions.ErrorCode;
import com.cocktailpick.back.common.exceptions.InvalidValueException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Embeddable
public class CocktailTags {
	@OneToMany(mappedBy = "cocktail", cascade = CascadeType.PERSIST, orphanRemoval = true, fetch = FetchType.EAGER)
	private List<CocktailTag> cocktailTags = new ArrayList<>();

	public static CocktailTags empty() {
		return new CocktailTags();
	}

	public List<Tag> getTags() {
		return cocktailTags.stream()
			.map(CocktailTag::getTag)
			.collect(Collectors.toList());
	}

	public void addCocktailTag(CocktailTag cocktailTag) {
		if (isContainCocktailTag(cocktailTag)) {
			throw new InvalidValueException(ErrorCode.TAG_DUPLICATED);
		}

		cocktailTags.add(cocktailTag);
	}

	private boolean isContainCocktailTag(CocktailTag other) {
		return cocktailTags.stream()
			.anyMatch(cocktailTag -> cocktailTag.equals(other));
	}

	public boolean notContainsTag(Tag tag) {
		return cocktailTags.stream()
			.noneMatch(cocktailTag -> cocktailTag.containsTag(tag));
	}

	public void clear() {
		cocktailTags.clear();
	}

	public void deleteCocktailTag(CocktailTag cocktailTag) {
		cocktailTags.remove(cocktailTag);
	}

	public boolean containTagIds(List<Long> tagIds) {
		return tagIds.stream()
			.allMatch(this::containTagId);
	}

	private boolean containTagId(Long tagId) {
		return cocktailTags.stream()
				.map(CocktailTag::tagId)
			.anyMatch(oneTagId -> oneTagId.equals(tagId));
	}
}
