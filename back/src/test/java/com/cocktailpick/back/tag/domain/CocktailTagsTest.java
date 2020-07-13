package com.cocktailpick.back.tag.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CocktailTagsTest {
	@DisplayName("빈 CocktailTags를 생성한다.")
	@Test
	void empty() {
		CocktailTags actual = CocktailTags.empty();

		assertThat(actual).isInstanceOf(CocktailTags.class);
	}
}