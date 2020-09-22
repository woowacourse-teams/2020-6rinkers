package com.cocktailpick.core.terminology.domain;

import static org.assertj.core.api.Assertions.*;

import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class TerminologyTypeTest {

	@DisplayName("TerminologyType에 포함되어 있는 name인 경우 알맞은 객체를 반환한다.")
	@MethodSource("provideTypeNameAndTerminologyType")
	@ParameterizedTest
	void of_WhenValidName_ReturnObject(String typeName, TerminologyType terminologyType) {
		assertThat(TerminologyType.of(typeName)).isEqualTo(terminologyType);
	}

	private static Stream<Arguments> provideTypeNameAndTerminologyType() {
		return Stream.of(
			Arguments.of("칵테일", TerminologyType.COCKTAIL),
			Arguments.of("술", TerminologyType.ALCOHOL),
			Arguments.of("제조법", TerminologyType.METHOD),
			Arguments.of("형태", TerminologyType.SHAPE)
		);
	}

	@DisplayName("TerminologyType에 포함되어 있지 않은 name인 경우 예외를 던진다.")
	@Test
	void of_WhenInvalidName_ThrowException() {
		assertThatThrownBy(() -> TerminologyType.of("음료수")).isInstanceOf(InvalidValueException.class)
			.hasMessage("해당 용어의 타입은 존재하지 않습니다.");
	}
}