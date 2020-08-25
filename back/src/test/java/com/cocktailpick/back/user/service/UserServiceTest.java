package com.cocktailpick.back.user.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cocktailpick.back.common.exceptions.ResourceNotFoundException;
import com.cocktailpick.back.user.domain.AuthProvider;
import com.cocktailpick.back.user.domain.Role;
import com.cocktailpick.back.user.domain.User;
import com.cocktailpick.back.user.domain.UserRepository;
import com.cocktailpick.back.user.dto.UserResponse;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
	@Mock
	private UserRepository userRepository;

	private UserService userService;

	private User user;

	@BeforeEach
	void setUp() {
		userService = new UserService(userRepository);

		user = new User();
		user.setId(1L);
		user.setEmail("a@email.com");
		user.setEmailVerified(true);
		user.setImageUrl("image.com");
		user.setName("hi");
		user.setPassword("password");
		user.setProvider(AuthProvider.local);
		user.setRole(Role.ROLE_USER);
		user.setProviderId("local");
	}

	@DisplayName("내 정보를 조회한다.")
	@Test
	void findMe() {
		when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

		assertThat(userService.findMe(1L)).isInstanceOf(UserResponse.class);
	}

	@DisplayName("내 정보가 없을 경우 예외가 발생한다.")
	@Test
	void findMeWithException() {
		when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

		assertThatThrownBy(() -> userService.findMe(1L))
			.isInstanceOf(ResourceNotFoundException.class);
	}
}
