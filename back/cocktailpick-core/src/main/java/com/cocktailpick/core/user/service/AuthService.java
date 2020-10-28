package com.cocktailpick.core.user.service;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cocktailpick.core.common.exceptions.AuthException;
import com.cocktailpick.core.common.exceptions.ErrorCode;
import com.cocktailpick.core.user.domain.AuthProvider;
import com.cocktailpick.core.user.domain.Role;
import com.cocktailpick.core.user.domain.User;
import com.cocktailpick.core.user.domain.UserRepository;
import com.cocktailpick.core.user.dto.AuthResponse;
import com.cocktailpick.core.user.dto.LoginRequest;
import com.cocktailpick.core.user.dto.SignUpRequest;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
	private final AuthenticationManager authenticationManager;
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final TokenProvider tokenProvider;

	public AuthResponse authenticateUser(LoginRequest loginRequest) {
		Authentication authentication;
		try {
			authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
					loginRequest.getEmail(),
					loginRequest.getPassword()
				)
			);
		} catch (AuthenticationException ex) {
			throw new AuthException(ErrorCode.BAD_LOGIN);
		}

		SecurityContextHolder.getContext().setAuthentication(authentication);

		return new AuthResponse(tokenProvider.createToken(authentication));
	}

	public Long registerUser(SignUpRequest signUpRequest) {
		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			throw new AuthException(ErrorCode.DUPLICATED_EMAIL);
		}

		User user = new User();
		user.setName(signUpRequest.getName());
		user.setEmail(signUpRequest.getEmail());
		user.setPassword(signUpRequest.getPassword());
		user.setProvider(AuthProvider.LOCAL);
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setRole(Role.ROLE_USER);

		User result = userRepository.spasswordEncoder.ave(user);
		return result.getId();
	}
}
