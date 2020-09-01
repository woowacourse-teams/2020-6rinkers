package com.cocktailpick.back.dictionary.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cocktailpick.back.dictionary.domain.Terminology;
import com.cocktailpick.back.dictionary.domain.TerminologyRepository;
import com.cocktailpick.back.dictionary.domain.TerminologyType;

@ExtendWith(MockitoExtension.class)
class TerminologyServiceTest {
	private TerminologyService terminologyService;

	@Mock
	private TerminologyRepository terminologyRepository;

	private Terminology terminology;

	@BeforeEach
	void setUp() {
		terminologyService = new TerminologyService(terminologyRepository);

		terminology = Terminology.builder()
			.id(1L)
			.name("보드카")
			.terminologyType(TerminologyType.of("술"))
			.description("러시아의 술입니다.")
			.imageUrl("https://media-verticommnetwork1.netdna-ssl.com/wines/absolut-vodka-45l-434781.jpg")
			.build();
	}

	@DisplayName("용어를 생성한다.")
	@Test
	void save() {
		when(terminologyRepository.save(any())).thenReturn(terminology);

		Long persistId = terminologyService.save(terminology);

		verify(terminologyRepository).save(any());
		assertEquals(persistId, 1L);
	}
}