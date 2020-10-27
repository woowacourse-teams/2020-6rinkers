package com.cocktailpick.core.tag.domain;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.cocktailpick.core.cocktail.domain.Cocktail;
import com.cocktailpick.core.cocktail.domain.Flavor;
import com.cocktailpick.core.common.exceptions.ErrorCode;
import com.cocktailpick.core.common.exceptions.InvalidValueException;
import com.google.common.collect.Lists;

class CocktailTagsTest {
	private Tag tag;

	private Flavor flavor;

	private Cocktail blueHawaii;

	@BeforeEach
	void setUp() {
		tag = Tag.builder().name("두강맛").tagType(TagType.FLAVOR).build();

		flavor = Flavor.builder()
			.bitter(true)
			.sour(true)
			.sweet(false)
			.build();

		blueHawaii = Cocktail.builder()
			.abv(40)
			.description("두강 맛 칵테일")
			.flavor(flavor)
			.imageUrl("https://naver.com")
			.name("DOO")
			.origin("두원이는 강하다.")
			.build();
	}

	@DisplayName("빈 CocktailTags를 생성한다.")
	@Test
	void empty() {
		CocktailTags actual = CocktailTags.empty();

		assertThat(actual).isInstanceOf(CocktailTags.class);
	}

	@DisplayName("CocktailTags에 CocktailTag를 추가한다.")
	@Test
	void add() {
		CocktailTags cocktailTags = new CocktailTags();

		CocktailTag cocktailTag = new CocktailTag();

		cocktailTag.setTag(tag);
		cocktailTag.setCocktail(blueHawaii);

		cocktailTags.addCocktailTag(cocktailTag);

		assertThat(cocktailTags.getTags()).hasSize(1);
	}

	@DisplayName("중복된 CocktailTag 추가 시 예외를 발생시킨다.")
	@Test
	void addDuplicatedCocktailTag() {
		CocktailTags cocktailTags = new CocktailTags();

		CocktailTag cocktailTag = new CocktailTag();

		cocktailTag.setTag(tag);
		cocktailTag.setCocktail(blueHawaii);

		cocktailTags.addCocktailTag(cocktailTag);

		assertThatThrownBy(() -> cocktailTags.addCocktailTag(cocktailTag))
			.isInstanceOf(InvalidValueException.class);
	}

	@DisplayName("CocktailTags가 태그 목록을 전부 포함하는지 확인한다.")
	@Test
	void containTags() {
		Tag zeroId = Tag.builder()
			.id(0L)
			.build();

		Tag oneId = Tag.builder()
			.id(1L)
			.build();

		CocktailTag ofZeroId = new CocktailTag();
		ofZeroId.setTag(zeroId);

		CocktailTag ofOneId = new CocktailTag();
		ofOneId.setTag(oneId);

		CocktailTags cocktailTags = new CocktailTags(Arrays.asList(ofZeroId, ofOneId));

		assertThat(cocktailTags.containTagIds(Arrays.asList(0L, 1L))).isTrue();
	}

	@DisplayName("CocktailTags가 태그 목록을 전부 포함하지 않는 경우를 확인한다.")
	@Test
	void containTags_WhenCocktailTagsNotContainTagIds() {
		Tag zeroId = Tag.builder()
			.id(0L)
			.build();

		Tag oneId = Tag.builder()
			.id(1L)
			.build();

		CocktailTag ofZeroId = new CocktailTag();
		ofZeroId.setTag(zeroId);

		CocktailTag ofOneId = new CocktailTag();
		ofOneId.setTag(oneId);

		CocktailTags cocktailTags = new CocktailTags(Arrays.asList(ofZeroId, ofOneId));

		assertThat(cocktailTags.containTagIds(Arrays.asList(0L, 1L, 3L))).isFalse();
	}

	@DisplayName("CocktailTag를 추가한다.")
	@Test
	void addCocktailTag() {
		List<CocktailTag> mocks = mock(List.class);
		CocktailTags cocktailTags = new CocktailTags(mocks);

		CocktailTag cocktailTag = new CocktailTag();
		cocktailTags.addCocktailTag(cocktailTag);

		verify(mocks).add(cocktailTag);
	}

	@DisplayName("중복된 CocktailTag를 추가할 경우 예외를 발생시킨다.")
	@Test
	void addCocktailTag_WhenDuplicated() {
		CocktailTag cocktailTag = new CocktailTag();
		CocktailTags cocktailTags = new CocktailTags(Lists.newArrayList(cocktailTag));

		assertThatThrownBy(() -> {
			cocktailTags.addCocktailTag(cocktailTag);
		}).isInstanceOf(InvalidValueException.class)
			.extracting("errorCode").isEqualTo(ErrorCode.TAG_DUPLICATED);
	}
}
