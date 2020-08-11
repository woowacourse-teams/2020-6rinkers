package com.cocktailpick.back.common.exceptions;

import org.springframework.security.core.AuthenticationException;

public class OAuth2AuthenticationProcessingException extends AuthenticationException {
	public OAuth2AuthenticationProcessingException(String msg, Throwable t) {
		super(msg, t);
	}

	public OAuth2AuthenticationProcessingException(String msg) {
		super(msg);
	}
}
