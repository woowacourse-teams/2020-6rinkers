package com.cocktailpick.back.user.service;

import org.springframework.stereotype.Service;

import com.cocktailpick.back.cocktail.domain.CocktailRepository;
import com.cocktailpick.back.common.exceptions.ResourceNotFoundException;
import com.cocktailpick.back.favorite.dto.FavoriteRequest;
import com.cocktailpick.back.favorite.service.FavoriteRepository;
import com.cocktailpick.back.user.domain.User;
import com.cocktailpick.back.user.domain.UserRepository;
import com.cocktailpick.back.user.dto.UserResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
public class UserService {
	private final UserRepository userRepository;
	private final FavoriteRepository favoriteRepository;
	private final CocktailRepository cocktailRepository;

	public UserResponse findMe(Long id) {
		User user = userRepository.findById(id)
			.orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
		return UserResponse.of(user);
	}

	public Long addFavorite(User user, FavoriteRequest favoriteRequest) {
		return 1L;
	}

}
