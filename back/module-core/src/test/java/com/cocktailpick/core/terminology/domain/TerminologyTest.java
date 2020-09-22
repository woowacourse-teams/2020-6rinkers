package com.cocktailpick.core.terminology.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TerminologyTest {
	private static final String VODKA_IMAGE_URL = "https://media-verticommnetwork1.netdna-ssl.com/wines/absolut-vodka-45l-434781.jpg";

	@DisplayName("용어를 수정한다.")
	@Test
	void update() {
		Terminology terminology = Terminology.builder()
			.id(1L)
			.name("보드카")
			.terminologyType(TerminologyType.of("술"))
			.description("러시아의 술입니다.")
			.imageUrl(VODKA_IMAGE_URL)
			.build();

		Terminology updatingTerminology = Terminology.builder()
			.name(terminology.getName())
			.description("보드카는 도수가 높습니다.")
			.terminologyType(terminology.getTerminologyType())
			.imageUrl(terminology.getImageUrl())
			.build();

		terminology.update(updatingTerminology);

		assertThat(terminology.getDescription()).isEqualTo("보드카는 도수가 높습니다.");
	}
}