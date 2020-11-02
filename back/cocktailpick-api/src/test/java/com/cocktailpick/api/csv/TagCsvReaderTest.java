package com.cocktailpick.api.csv;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import com.cocktailpick.api.tag.controller.Fixtures;
import com.cocktailpick.core.tag.domain.TagType;
import com.cocktailpick.core.tag.dto.TagRequest;

class TagCsvReaderTest {
	private MultipartFile multipartFile;

	@BeforeEach
	void setUp() {
		String content = Fixtures.THREE_TAGS_CSV_CONTENT;
		multipartFile = new MockMultipartFile("file", "태그.csv", "text/csv",
			content.getBytes());
	}

	@Test
	void getTags() throws Exception {
		TagCsvReader tagCsvReader = new TagCsvReader(OpenCsvReader.from(multipartFile.getInputStream()));

		List<TagRequest> actual = tagCsvReader.getTagRequests();

		assertAll(
			() -> assertThat(actual).hasSize(3),
			() -> assertThat(actual.get(0).getName()).isEqualTo("두강"),
			() -> assertThat(actual.get(0).getTagType()).isEqualTo(TagType.CONCEPT.getTagType()),
			() -> assertThat(actual.get(1).getName()).isEqualTo("두중"),
			() -> assertThat(actual.get(1).getTagType()).isEqualTo(TagType.ABV.getTagType()),
			() -> assertThat(actual.get(2).getName()).isEqualTo("두약"),
			() -> assertThat(actual.get(2).getTagType()).isEqualTo(TagType.INGREDIENT.getTagType())
		);
	}
}