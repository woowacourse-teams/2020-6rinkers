package com.cocktailpick.api.security.oauth2;

import org.springframework.security.core.AuthenticationException;

public class OAuth2AuthenticationProcessingException extends AuthenticationException {
	public OAuth2AuthenticationProcessingException(String msg) {
		super(msg);
	}
}
