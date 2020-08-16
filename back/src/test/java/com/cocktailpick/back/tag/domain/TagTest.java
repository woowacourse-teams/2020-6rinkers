package com.cocktailpick.back.tag.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class TagTest {
	@DisplayName("같은 태그인지 확인한다.")
	@ParameterizedTest
	@CsvSource({"초코,초코,true", "탄산,분위기있는,false", "연인과함께,연인과함께,true", "부드러운,부드러울뻔한,false"})
	void isSameNameTest(String name1, String name2, boolean expect) {
		Tag tag1 = new Tag(name1, TagType.CONCEPT);
		Tag tag2 = new Tag(name2, TagType.CONCEPT);

		assertThat(tag1.isSameName(tag2)).isEqualTo(expect);
	}

	@DisplayName("태그 타입을 반환한다.")
	@ParameterizedTest
	@CsvSource({"ABV,도수", "FLAVOR,맛", "TEXTURE,식감", "CONCEPT,컨셉", "INGREDIENT,재료", "DISLIKE,꺼릴만한 것"})
	void getTagTypeTest(TagType tagType, String expect) {
		Tag tag = new Tag("태그명", tagType);

		assertThat(tag.getTagType()).isEqualTo(expect);
	}
}
