package com.cocktailpick.back.common.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class AuthException extends BusinessException {
	public AuthException(ErrorCode errorCode) {
		super(errorCode);
	}
}
