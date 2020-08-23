package com.cocktailpick.back.user.service;

import org.springframework.stereotype.Service;

import com.cocktailpick.back.cocktail.domain.CocktailRepository;
import com.cocktailpick.back.favorite.dto.FavoriteRequest;
import com.cocktailpick.back.favorite.service.FavoriteRepository;
import com.cocktailpick.back.user.domain.User;
import com.cocktailpick.back.user.domain.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
@Service
public class UserService {
	private final UserRepository userRepository;
	private final FavoriteRepository favoriteRepository;
	private final CocktailRepository cocktailRepository;

	public Long addFavorite(User user, FavoriteRequest favoriteRequest) {
		return 1L;
	}
}
