package com.cocktailpick.core.user.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cocktailpick.core.cocktail.domain.Cocktail;
import com.cocktailpick.core.cocktail.domain.CocktailRepository;
import com.cocktailpick.core.common.exceptions.AuthException;
import com.cocktailpick.core.favorite.domain.Favorite;
import com.cocktailpick.core.favorite.domain.FavoriteRepository;
import com.cocktailpick.core.favorite.domain.Favorites;
import com.cocktailpick.core.favorite.dto.FavoriteRequest;
import com.cocktailpick.core.user.domain.AuthProvider;
import com.cocktailpick.core.user.domain.Role;
import com.cocktailpick.core.user.domain.User;
import com.cocktailpick.core.user.domain.UserRepository;
import com.cocktailpick.core.user.dto.SignUpRequest;
import com.cocktailpick.core.user.dto.UserUpdateRequest;

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

	private Cocktail cocktail;

	private Cocktail cocktail2;

	private Favorite favorite;

	@BeforeEach
	void setUp() {
		userService = new UserService(userRepository, favoriteRepository, cocktailRepository);

		cocktail = Cocktail.builder()
			.id(1L)
			.name("toney")
			.build();

		cocktail2 = Cocktail.builder()
			.id(2L)
			.name("doo")
			.build();

		Favorites favorites = Favorites.empty();

		favorite = new Favorite(1L, user, cocktail);

		favorites.addFavorite(favorite);

		user = new User();
		user.setId(1L);
		user.setEmail("a@email.com");
		user.setEmailVerified(true);
		user.setImageUrl("image.com");
		user.setName("toney");
		user.setPassword("password");
		user.setProvider(AuthProvider.local);
		user.setRole(Role.ROLE_USER);
		user.setProviderId("local");
		user.setFavorites(favorites);
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

		assertThat(userService.registerUser(signUpRequest)).isEqualTo(user.getId());
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

		assertThatThrownBy(() -> userService.registerUser(signUpRequest))
			.isInstanceOf(AuthException.class)
			.hasMessageContaining("존재하는");
	}

	@DisplayName("사용자 이름을 수정한다.")
	@Test
	void updateUser() {
		UserUpdateRequest userUpdateRequest = new UserUpdateRequest("작은곰");
		userService.updateUser(user, userUpdateRequest);

		assertThat(user.getName()).isEqualTo(userUpdateRequest.getName());
	}

	@DisplayName("즐겨찾기를 조회한다.")
	@Test
	void findFavoritesTest() {
		assertThat(userService.findFavorites(user).size()).isEqualTo(1);
	}

	@DisplayName("즐겨찾기한 칵테일의 id를 조회한다.")
	@Test
	void findFavoriteCocktailIdsTest() {
		assertThat(userService.findFavoriteCocktailIds(user).getIds().size()).isEqualTo(1);
	}

	@DisplayName("즐겨찾기를 추가한다.")
	@Test
	void addFavoriteTest() {
		when(cocktailRepository.findById(anyLong())).thenReturn(Optional.of(cocktail2));
		when(favoriteRepository.save(any())).thenReturn(favorite);
		userService.addFavorite(user, new FavoriteRequest(2L));

		verify(favoriteRepository).save(any());
	}

	@DisplayName("즐겨찾기를 삭제한다.")
	@Test
	void deleteFavoriteTest() {
		userService.deleteFavorite(user, 1L);

		assertThat(user.getFavorites().getFavorites().size()).isZero();
	}

	@DisplayName("회원 탈퇴한다.")
	@Test
	void deleteCurrentUser() {
		userService.deleteCurrentUser(user);

		verify(userRepository).deleteById(anyLong());
	}
}
