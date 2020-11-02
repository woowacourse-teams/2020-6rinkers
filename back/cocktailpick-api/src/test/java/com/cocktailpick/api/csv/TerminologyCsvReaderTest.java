package com.cocktailpick.api.csv;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import com.cocktailpick.api.terminology.controller.Fixtures;
import com.cocktailpick.core.terminology.dto.TerminologyRequest;

class TerminologyCsvReaderTest {
	private MultipartFile multipartFile;

	@BeforeEach
	void setUp() {
		String content = Fixtures.FOUR_TERMINOLOGIES_CSV_CONTENT;
		multipartFile = new MockMultipartFile("file", "용어.csv", "text/csv", content.getBytes());
	}

	@DisplayName("csv 파일로 Terminology 객채들을 생성한다.")
	@Test
	void getTerminologies() throws IOException {
		TerminologyCsvReader terminologyCsvReader = new TerminologyCsvReader(
			OpenCsvReader.from(multipartFile.getInputStream()));

		List<TerminologyRequest> actual = terminologyCsvReader.getTerminologyRequests();

		assertAll(
			() -> assertThat(actual).hasSize(4),
			() -> assertThat(actual.get(0).getName()).isEqualTo("가니쉬"),
			() -> assertThat(actual.get(0).getTerminologyType()).isEqualTo("칵테일"),
			() -> assertThat(actual.get(0).getDescription()).contains("장식"),
			() -> assertThat(actual.get(0).getImageUrl()).contains("amazonaws.com/garnish.png"),
			() -> assertThat(actual.get(3).getName()).isEqualTo("레이어링"),
			() -> assertThat(actual.get(3).getTerminologyType()).isEqualTo("제조법"),
			() -> assertThat(actual.get(3).getDescription()).contains("재료들을 층을"),
			() -> assertThat(actual.get(3).getImageUrl()).contains("amazonaws.com/layering.png")
		);
	}
}