package com.cocktailpick.back.user.service;

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
import com.cocktailpick.back.favorite.domain.Favorite;
import com.cocktailpick.back.favorite.domain.Favorites;
import com.cocktailpick.back.favorite.dto.FavoriteRequest;
import com.cocktailpick.back.favorite.service.FavoriteRepository;
import com.cocktailpick.back.user.domain.User;
import com.cocktailpick.back.user.domain.UserRepository;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
	private UserService userService;

	@Mock
	private UserRepository userRepository;

	@Mock
	private FavoriteRepository favoriteRepository;

	@Mock
	private CocktailRepository cocktailRepository;

	private User user;

	private Cocktail cocktail;

	private Favorite favorite;

	@BeforeEach
	void setUp() {
		userService = new UserService(userRepository, favoriteRepository, cocktailRepository);
		user = User.builder()
			.name("toney")
			.favorites(Favorites.empty())
			.build();
		cocktail = Cocktail.builder()
			.id(1L)
			.name("toney")
			.build();
		favorite = new Favorite(1L, user, cocktail);
	}

	@DisplayName("즐겨찾기를 추가한다.")
	@Test
	void addFavoriteTest() {
		when(cocktailRepository.findById(anyLong())).thenReturn(Optional.of(cocktail));
		when(favoriteRepository.save(any())).thenReturn(favorite);
		userService.addFavorite(user, new FavoriteRequest(1L));

		verify(favoriteRepository).save(any());
	}
}
