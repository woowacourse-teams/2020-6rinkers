package com.cocktailpick.back.dictionary.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.cocktailpick.back.common.exceptions.InvalidValueException;

class TerminologyTypeTest {
	@DisplayName("TerminologyType에 포함되어 있는 name인 경우 알맞은 객체를 반환한다.")
	@Test
	void of_WhenValidName_ReturnObject() {
		assertThat(TerminologyType.of("술"Â).isInstanceOf(TerminologyType.class);
	}

	@DisplayName("TerminologyType에 포함되어 있지 않은 name인 경우 예외를 던진다.")
	@Test
	void of_WhenInvalidName_ThrowException() {
		assertThatThrownBy(() -> TerminologyType.of("음료수")).isInstanceOf(InvalidValueException.class)
			.hasMessage("해당 용어의 타입은 존재하지 않습니다.");
	}
}