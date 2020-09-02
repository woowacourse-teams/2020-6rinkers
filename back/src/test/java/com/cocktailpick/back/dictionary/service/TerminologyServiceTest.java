package com.cocktailpick.back.dictionary.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cocktailpick.back.common.exceptions.EntityNotFoundException;
import com.cocktailpick.back.common.exceptions.ErrorCode;
import com.cocktailpick.back.dictionary.domain.Terminology;
import com.cocktailpick.back.dictionary.domain.TerminologyRepository;
import com.cocktailpick.back.dictionary.domain.TerminologyType;

@ExtendWith(MockitoExtension.class)
class TerminologyServiceTest {
	private static final String VODKA_IMAGE_URL = "https://media-verticommnetwork1.netdna-ssl.com/wines/absolut-vodka-45l-434781.jpg";

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
			.imageUrl(VODKA_IMAGE_URL)
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

	@DisplayName("모든 용어를 조회한다.")
	@Test
	void findAllTerminologies() {
		List<Terminology> terminologies = Arrays.asList(
			terminology,
			Terminology.builder()
				.id(2L)
				.name("지거")
				.terminologyType(TerminologyType.of("칵테일"))
				.description("음료를 측정하는 도구입니다.")
				.imageUrl(VODKA_IMAGE_URL)
				.build()
		);

		when(terminologyRepository.findAll()).thenReturn(terminologies);

		List<Terminology> persistTerminologies = terminologyRepository.findAll();

		verify(terminologyRepository).findAll();
		assertThat(persistTerminologies).hasSize(2);
	}

	@DisplayName("단일 용어를 조회한다.")
	@Test
	void findTerminology() {
		when(terminologyRepository.findById(1L)).thenReturn(Optional.of(terminology));

		Terminology persistTerminology = terminologyRepository.findById(1L).get();

		verify(terminologyRepository).findById(anyLong());
		assertThat(persistTerminology).isEqualTo(terminology);
	}

	@DisplayName("단일 용어 조회시 잘못된 id가 입력되면 예외가 발생한다.")
	@Test
	void findTerminology_WhenWrongId_ThrowException() {
		when(terminologyRepository.findById(2L))
			.thenThrow(new EntityNotFoundException(ErrorCode.TERMINOLOGY_NOT_FOUND));

		assertThatThrownBy(() -> terminologyRepository.findById(2L))
			.isInstanceOf(EntityNotFoundException.class)
			.hasMessage(ErrorCode.TERMINOLOGY_NOT_FOUND.getMessage());
	}

	@DisplayName("용어를 수정한다.")
	@Test
	void update() {
		when(terminologyRepository.findById(anyLong())).thenReturn(Optional.of(terminology));

		Terminology updatingTerminology = Terminology.builder()
			.name(terminology.getName())
			.description("보드카는 도수가 높습니다.")
			.terminologyType(terminology.getTerminologyType())
			.imageUrl(terminology.getImageUrl())
			.build();

		terminologyService.update(updatingTerminology, 1L);

		assertEquals(terminology.getDescription(), "보드카는 도수가 높습니다.");
	}
}