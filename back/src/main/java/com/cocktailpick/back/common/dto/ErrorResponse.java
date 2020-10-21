package com.cocktailpick.back.common.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.validation.BindingResult;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.cocktailpick.back.common.exceptions.ErrorCode;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ErrorResponse {
	private String message;

	private List<FieldError> errors;

	private String code;

	private ErrorResponse(ErrorCode code, List<FieldError> errors) {
		this.message = code.getMessage();
		this.errors = errors;
		this.code = code.getCode();
	}

	private ErrorResponse(ErrorCode code) {
		this.message = code.getMessage();
		this.code = code.getCode();
		this.errors = new ArrayList<>();
	}

	public static ErrorResponse of(ErrorCode code, BindingResult bindingResult) {
		return new ErrorResponse(code, FieldError.of(bindingResult));
	}

	public static ErrorResponse of(ErrorCode code) {
		return new ErrorResponse(code);
	}

	public static ErrorResponse of(MethodArgumentTypeMismatchException e) {
		String value = Optional.ofNullable(e.getValue())
			.map(Object::toString)
			.orElse("");

		List<ErrorResponse.FieldError> errors = ErrorResponse.FieldError.of(
			e.getName(), value, e.getErrorCode());
		return new ErrorResponse(ErrorCode.INVALID_TYPE_VALUE, errors);
	}

	@Getter
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	public static class FieldError {
		private String field;
		private String value;
		private String reason;

		private FieldError(String field, String value, String reason) {
			this.field = field;
			this.value = value;
			this.reason = reason;
		}

		public static List<FieldError> of(String field, String value, String reason) {
			List<FieldError> fieldErrors = new ArrayList<>();
			fieldErrors.add(new FieldError(field, value, reason));
			return fieldErrors;
		}

		private static List<FieldError> of(BindingResult bindingResult) {
			List<org.springframework.validation.FieldError> fieldErrors = bindingResult
				.getFieldErrors();
			return fieldErrors.stream()
				.map(error -> new FieldError(
					error.getField(),
					error.getRejectedValue() == null ? "" :
						error.getRejectedValue().toString(),
					error.getDefaultMessage()))
				.collect(Collectors.toList());
		}
	}
}