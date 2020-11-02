package com.cocktailpick.core.common.exceptions;

public class AuthException extends BusinessException {
	public AuthException(ErrorCode errorCode) {
		super(errorCode);
	}
}
