package com.cocktailpick.back.user.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;

import com.cocktailpick.back.common.exceptions.BadRequestException;
import com.cocktailpick.back.security.TokenProvider;
import com.cocktailpick.back.user.domain.AuthProvider;
import com.cocktailpick.back.user.domain.Role;
import com.cocktailpick.back.user.domain.User;
import com.cocktailpick.back.user.domain.UserRepository;
import com.cocktailpick.back.user.dto.LoginRequest;
import com.cocktailpick.back.user.dto.SignUpRequest;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {
	@Mock
	private AuthenticationManager authenticationManager;

	@Mock
	private PasswordEncoder passwordEncoder;

	@Mock
	private UserRepository userRepository;

	@Mock
	private TokenProvider tokenProvider;

	private AuthService authService;

	@BeforeEach
	void setUp() {
		authService = new AuthService(authenticationManager, userRepository, passwordEncoder,
			tokenProvider);
	}

	@DisplayName("로그인")
	@Test
	void authenticateUser() {
		LoginRequest loginRequest = new LoginRequest("a@email.com", "password");

		when(tokenProvider.createToken(any())).thenReturn("TOKEN");

		assertThat(authService.authenticateUser(loginRequest)).isNotNull();
	}

	@DisplayName("회원가입을 한다.")
	@Test
	void registerUser() {
		SignUpRequest signUpRequest = new SignUpRequest("아이디", "a@email.com", "password");

		User user = new User();
		user.setId(1L);
		user.setEmail("a@email.com");
		user.setEmailVerified(true);
		user.setImageUrl("image.com");
		user.setName("hi");
		user.setPassword("password");
		user.setProvider(AuthProvider.local);
		user.setRole(Role.ROLE_USER);
		user.setProviderId("local");

		when(userRepository.existsByEmail(any())).thenReturn(false);
		when(userRepository.save(any())).thenReturn(user);

		assertThat(authService.registerUser(signUpRequest)).isEqualTo(user.getId());
	}

	@DisplayName("중복되는 이메일일 경우 예외처리한다.")
	@Test
	void registerUserWithException() {
		SignUpRequest signUpRequest = new SignUpRequest("아이디", "a@email.com", "password");

		User user = new User();
		user.setId(1L);
		user.setEmail("a@email.com");
		user.setEmailVerified(true);
		user.setImageUrl("image.com");
		user.setName("hi");
		user.setPassword("password");
		user.setProvider(AuthProvider.local);
		user.setRole(Role.ROLE_USER);
		user.setProviderId("local");

		when(userRepository.existsByEmail(any())).thenReturn(true);

		assertThatThrownBy(() -> authService.registerUser(signUpRequest))
			.isInstanceOf(BadRequestException.class)
			.hasMessageContaining("존재하는");
	}
}