package com.cocktailpick.api.security;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.cocktailpick.core.common.exceptions.AuthException;
import com.cocktailpick.core.common.exceptions.ErrorCode;
import com.cocktailpick.core.user.dto.LoginRequest;
import com.fasterxml.jackson.databind.ObjectMapper;

public class LoginFilter extends UsernamePasswordAuthenticationFilter {
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws
		AuthenticationException {
		return super.attemptAuthentication(request, response);
	}

	@Override
	protected String obtainUsername(HttpServletRequest request) {
		return getLoginRequest(request).getEmail();
	}

	@Override
	protected String obtainPassword(HttpServletRequest request) {
		return getLoginRequest(request).getPassword();
	}

	private LoginRequest getLoginRequest(HttpServletRequest request) {
		try {
			return new ObjectMapper().readValue(request.getInputStream(), LoginRequest.class);
		} catch (IOException e) {
			throw new AuthException(ErrorCode.BAD_LOGIN);
		}
	}
}