package com.cocktailpick.core.terminology.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

import com.cocktailpick.core.terminology.domain.Terminology;
import com.cocktailpick.core.util.csv.OpenCsvReader;

class TerminologyCsvReaderTest {
	private MultipartFile multipartFile;

	@BeforeEach
	void setUp() {
		String content = Fixtures.FOUR_TERMINOLOGIES_CSV_CONTENT;
		multipartFile = new MockMultipartFile("file", "용어.csv", "text/csv", content.getBytes());
	}

	@DisplayName("csv 파일로 Terminology 객채들을 생성한다.")
	@Test
	void getTerminologies() {
		TerminologyCsvReader terminologyCsvReader = new TerminologyCsvReader(OpenCsvReader.from(multipartFile));

		List<Terminology> actual = terminologyCsvReader.getTerminologies();

		assertAll(
			() -> assertThat(actual).hasSize(4),
			() -> assertThat(actual.get(0).getName()).isEqualTo("가니쉬"),
			() -> assertThat(actual.get(0).getTerminologyType().getTypeName()).isEqualTo("칵테일"),
			() -> assertThat(actual.get(0).getDescription()).contains("장식"),
			() -> assertThat(actual.get(0).getImageUrl()).contains("amazonaws.com/garnish.png"),
			() -> assertThat(actual.get(3).getName()).isEqualTo("레이어링"),
			() -> assertThat(actual.get(3).getTerminologyType().getTypeName()).isEqualTo("제조법"),
			() -> assertThat(actual.get(3).getDescription()).contains("재료들을 층을"),
			() -> assertThat(actual.get(3).getImageUrl()).contains("amazonaws.com/layering.png")
		);
	}
}