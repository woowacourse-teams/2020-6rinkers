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

import com.cocktailpick.back.cocktail.domain.Cocktail;
import com.cocktailpick.back.cocktail.domain.CocktailRepository;
import com.cocktailpick.back.common.exceptions.ResourceNotFoundException;
import com.cocktailpick.back.favorite.domain.Favorite;
import com.cocktailpick.back.favorite.dto.FavoriteRequest;
import com.cocktailpick.back.favorite.service.FavoriteRepository;
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

	@Mock
	private FavoriteRepository favoriteRepository;

	@Mock
	private CocktailRepository cocktailRepository;

	private User user;

	private User user2;

	private Cocktail cocktail;

	private Favorite favorite;

	@BeforeEach
	void setUp() {
		userService = new UserService(userRepository, favoriteRepository, cocktailRepository);

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

		user2 = User.builder()
			.name("toney")
			.build();
		cocktail = Cocktail.builder()
			.id(1L)
			.name("toney")
			.build();
		favorite = new Favorite(1L, user, cocktail);

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

	@DisplayName("즐겨찾기를 추가한다.")
	@Test
	void addFavoriteTest() {
		when(cocktailRepository.findById(anyLong())).thenReturn(Optional.of(cocktail));
		when(favoriteRepository.save(any())).thenReturn(favorite);
		userService.addFavorite(user2, new FavoriteRequest(1L));

		verify(favoriteRepository).save(any());
	}
}
