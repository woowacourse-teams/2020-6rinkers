package com.cocktailpick.back.tag.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class TagTypeTest {
	@DisplayName("입력한 태그 타입이 나오는지 확인한다.")
	@ParameterizedTest
	@CsvSource({"도수,ABV", "컨셉,CONCEPT", "맛,FLAVOR", "재료,INGREDIENT", "식감,TEXTURE", "꺼릴만한 것,DISLIKE"})
	void ofTest(String inputTagType, TagType expect) {
		assertThat(TagType.of(inputTagType)).isEqualTo(expect);
	}
}
