package com.cocktailpick.core.common.exceptions;

public class EntityNotFoundException extends BusinessException {
	public EntityNotFoundException(ErrorCode errorCode) {
		super(errorCode);
	}
}
