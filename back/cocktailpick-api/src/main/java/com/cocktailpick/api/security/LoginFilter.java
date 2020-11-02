package com.cocktailpick.api.security;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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
		if (!request.getMethod().equals("POST")) {
			throw new AuthenticationServiceException(
				"Authentication method not supported: " + request.getMethod());
		}

		LoginRequest loginRequest;
		try {
			loginRequest = new ObjectMapper().readValue(request.getInputStream(), LoginRequest.class);
		} catch (IOException e) {
			throw new AuthException(ErrorCode.BAD_LOGIN);
		}

		String username = loginRequest.getEmail();
		String password = loginRequest.getPassword();

		if (username == null) {
			username = "";
		}

		if (password == null) {
			password = "";
		}

		username = username.trim();

		UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
			username, password);

		// Allow subclasses to set the "details" property
		setDetails(request, authRequest);

		return this.getAuthenticationManager().authenticate(authRequest);
	}
}