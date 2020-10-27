package com.cocktailpick.core.common.exceptions;

public class InvalidValueException extends BusinessException {
	public InvalidValueException(ErrorCode errorCode) {
		super(errorCode);
	}
}
