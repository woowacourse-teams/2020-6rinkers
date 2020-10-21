package com.cocktailpick.back.common.dto;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.cocktailpick.back.common.exceptions.ErrorCode;

@ExtendWith(MockitoExtension.class)
class ErrorResponseTest {

	@Mock
	private MethodArgumentTypeMismatchException methodArgumentTypeMismatchException;

	@DisplayName("MethodArgumentTypeMismatchException의 value가 null이 아닌 경우 ErrorResponse를 반환한다.")
	@Test
	void create() {
		given(methodArgumentTypeMismatchException.getValue()).willReturn("value");
		given(methodArgumentTypeMismatchException.getErrorCode()).willReturn(
			String.valueOf(ErrorCode.INVALID_TYPE_VALUE));
		given(methodArgumentTypeMismatchException.getName()).willReturn("값이 null이 아닌 예외");

		ErrorResponse actual = ErrorResponse.of(methodArgumentTypeMismatchException);

		assertThat(actual.getErrors().get(0).getValue()).isEqualTo("value");
	}

	@DisplayName("MethodArgumentTypeMismatchException의 value가 null일 경우 빈 스트링을 value로 가진 ErrorResponse를 반환한다.")
	@Test
	void create_Null() {
		given(methodArgumentTypeMismatchException.getValue()).willReturn(null);
		given(methodArgumentTypeMismatchException.getErrorCode()).willReturn(
			String.valueOf(ErrorCode.INVALID_TYPE_VALUE));
		given(methodArgumentTypeMismatchException.getName()).willReturn("값이 null인 예외");

		ErrorResponse actual = ErrorResponse.of(methodArgumentTypeMismatchException);

		assertThat(actual.getErrors().get(0).getValue()).isEmpty();
	}
}