package com.cocktailpick.back.tag.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import com.cocktailpick.back.common.csv.OpenCsvReader;
import com.cocktailpick.back.tag.domain.Tag;

class TagCsvReaderTest {
	private MultipartFile multipartFile;

	@BeforeEach
	void setUp() {
		String content = "태그\n두강\n두중\n두약";
		multipartFile = new MockMultipartFile("file", "태그.csv", "text/csv",
			content.getBytes());
	}

	@Test
	void getTags() {
		TagCsvReader tagCsvReader = new TagCsvReader(OpenCsvReader.from(multipartFile));

		List<Tag> actual = tagCsvReader.getTags();

		assertAll(
			() -> assertThat(actual).hasSize(3),
			() -> assertThat(actual.get(0).getName()).isEqualTo("두강"),
			() -> assertThat(actual.get(1).getName()).isEqualTo("두중"),
			() -> assertThat(actual.get(2).getName()).isEqualTo("두약")
		);
	}
}