package com.cocktailpick.core.terminology.service;

import static com.cocktailpick.core.common.exceptions.ErrorCode.*;
import static org.assertj.core.api.Assertions.*;
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

import com.cocktailpick.core.common.exceptions.EntityNotFoundException;
import com.cocktailpick.core.common.exceptions.ErrorCode;
import com.cocktailpick.core.common.exceptions.InvalidValueException;
import com.cocktailpick.core.terminology.domain.Terminology;
import com.cocktailpick.core.terminology.domain.TerminologyRepository;
import com.cocktailpick.core.terminology.domain.TerminologyType;
import com.cocktailpick.core.terminology.dto.TerminologyRequest;
import com.cocktailpick.core.terminology.dto.TerminologyResponse;

@ExtendWith(MockitoExtension.class)
class TerminologyServiceTest {
	private static final String VODKA_IMAGE_URL = "https://media-verticommnetwork1.netdna-ssl.com/wines/absolut-vodka-45l-434781.jpg";

	private TerminologyService terminologyService;

	@Mock
	private TerminologyRepository terminologyRepository;

	private TerminologyRequest terminologyRequest;

	private Terminology terminology;

	@BeforeEach
	void setUp() {
		terminologyService = new TerminologyService(terminologyRepository);

		terminologyRequest = TerminologyRequest.builder()
			.name("보드카")
			.terminologyType("술")
			.description("러시아의 술입니다.")
			.imageUrl(VODKA_IMAGE_URL)
			.build();

		terminology = Terminology.builder()
			.id(1L)
			.name("보드카")
			.terminologyType(TerminologyType.of("술"))
			.description("러시아의 술입니다.")
			.imageUrl(VODKA_IMAGE_URL)
			.build();
	}

	@DisplayName("용어를 저장한다.")
	@Test
	void save() {
		when(terminologyRepository.save(any())).thenReturn(terminology);

		Long persistId = terminologyService.save(terminologyRequest);

		verify(terminologyRepository).save(any());
		assertThat(persistId).isEqualTo(1L);
	}

	@DisplayName("이미 저장된 이름의 용어를 저장할 경우 예외가 발생한다.")
	@Test
	void saveWithException() {
		when(terminologyRepository.findByName(anyString())).thenReturn(Optional.of(terminology));

		assertThatThrownBy(() -> terminologyService.save(terminologyRequest))
			.isInstanceOf(InvalidValueException.class)
			.hasMessage(TERMINOLOGY_DUPLICATED.getMessage());
	}

	@DisplayName("다수의 용어 저장 요청을 수행한다.")
	@Test
	void saveAll() {
		List<TerminologyRequest> terminologyRequests = Arrays.asList(
			TerminologyRequest.builder().name("하나").terminologyType(TerminologyType.ALCOHOL.getTypeName()).build(),
			TerminologyRequest.builder().name("둘").terminologyType(TerminologyType.ALCOHOL.getTypeName()).build());
		terminologyService.saveAll(terminologyRequests);

		verify(terminologyRepository).saveAll(anyList());
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

		List<TerminologyResponse> terminologyResponses = terminologyService.findAllTerminologies();

		verify(terminologyRepository).findAll();
		assertThat(terminologyResponses).hasSize(2);
	}

	@DisplayName("단일 용어를 조회한다.")
	@Test
	void findTerminology() {
		when(terminologyRepository.findById(1L)).thenReturn(Optional.of(terminology));

		TerminologyResponse terminologyResponse = terminologyService.findTerminology(1L);

		verify(terminologyRepository).findById(anyLong());
		assertThat(terminologyResponse.getName()).isEqualTo(terminology.getName());
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

		TerminologyRequest updatingTerminologyRequest = TerminologyRequest.builder()
			.name(terminology.getName())
			.description("보드카는 도수가 높습니다.")
			.terminologyType("술")
			.imageUrl(terminology.getImageUrl())
			.build();

		terminologyService.update(updatingTerminologyRequest, 1L);

		assertThat(terminology.getDescription()).isEqualTo("보드카는 도수가 높습니다.");
	}

	@DisplayName("용어를 삭제한다.")
	@Test
	void delete() {
		terminologyService.delete(1L);

		verify(terminologyRepository).deleteById(anyLong());
	}
}