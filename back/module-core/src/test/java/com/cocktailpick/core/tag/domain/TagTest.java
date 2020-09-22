package com.cocktailpick.core.tag.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class TagTest {
	@DisplayName("같은 태그인지 확인한다.")
	@ParameterizedTest
	@CsvSource({"초코,초코,true", "탄산,분위기있는,false", "연인과함께,연인과함께,true", "부드러운,부드러울뻔한,false"})
	void isSameNameTest(String name1, String name2, boolean expect) {
		Tag tag1 = Tag.builder().name(name1).tagType(TagType.CONCEPT).build();
		Tag tag2 = Tag.builder().name(name2).tagType(TagType.CONCEPT).build();

		assertThat(tag1.isSameName(tag2)).isEqualTo(expect);
	}

	@DisplayName("태그 타입을 반환한다.")
	@ParameterizedTest
	@CsvSource({"ABV,ABV", "FLAVOR,FLAVOR", "TEXTURE,TEXTURE", "CONCEPT,CONCEPT", "INGREDIENT,INGREDIENT", "DISLIKE,DISLIKE"})
	void getTagTypeTest(TagType tagType, String expect) {
		Tag tag = Tag.builder().name("태그명").tagType(tagType).build();

		assertThat(tag.getTagType()).isEqualTo(expect);
	}
}
