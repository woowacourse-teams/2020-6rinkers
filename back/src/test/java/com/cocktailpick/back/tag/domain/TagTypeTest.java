package com.cocktailpick.back.tag.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class TagTypeTest {
	@DisplayName("입력한 태그 타입이 나오는지 확인한다.")
	@ParameterizedTest
	@CsvSource({"ABV,ABV", "CONCEPT,CONCEPT", "FLAVOR,FLAVOR", "INGREDIENT,INGREDIENT", "TEXTURE,TEXTURE", "DISLIKE,DISLIKE"})
	void ofTest(String inputTagType, TagType expect) {
		assertThat(TagType.of(inputTagType)).isEqualTo(expect);
	}
}
