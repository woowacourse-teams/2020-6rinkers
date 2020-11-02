package com.cocktailpick.core.user.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cocktailpick.core.cocktail.domain.Cocktail;
import com.cocktailpick.core.cocktail.domain.CocktailRepository;
import com.cocktailpick.core.cocktail.dto.CocktailResponse;
import com.cocktailpick.core.common.exceptions.AuthException;
import com.cocktailpick.core.common.exceptions.EntityNotFoundException;
import com.cocktailpick.core.common.exceptions.ErrorCode;
import com.cocktailpick.core.favorite.domain.Favorite;
import com.cocktailpick.core.favorite.domain.FavoriteRepository;
import com.cocktailpick.core.favorite.dto.FavoriteRequest;
import com.cocktailpick.core.user.domain.AuthProvider;
import com.cocktailpick.core.user.domain.Role;
import com.cocktailpick.core.user.domain.User;
import com.cocktailpick.core.user.domain.UserRepository;
import com.cocktailpick.core.user.dto.FavoriteCocktailIdsResponse;
import com.cocktailpick.core.user.dto.SignUpRequest;
import com.cocktailpick.core.user.dto.UserUpdateRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
@Service
public class UserService {
	private final UserRepository userRepository;
	private final FavoriteRepository favoriteRepository;
	private final CocktailRepository cocktailRepository;

	public Long registerUser(SignUpRequest signUpRequest) {
		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			throw new AuthException(ErrorCode.DUPLICATED_EMAIL);
		}

		User user = new User();
		user.setName(signUpRequest.getName());
		user.setEmail(signUpRequest.getEmail());
		user.setPassword(signUpRequest.getPassword());
		user.setProvider(AuthProvider.LOCAL);
		user.setRole(Role.ROLE_USER);

		User result = userRepository.save(user);
		return result.getId();
	}

	@Transactional
	public void deleteCurrentUser(User user) {
		userRepository.deleteById(user.getId());
	}

	@Transactional(readOnly = true)
	public List<CocktailResponse> findFavorites(User user) {
		return user.getFavorites().getFavorites().stream()
			.map(Favorite::getCocktail)
			.map(CocktailResponse::of)
			.collect(Collectors.toList());
	}

	public void updateUser(User user, UserUpdateRequest userRequest) {
		user.setName(userRequest.getName());
		userRepository.save(user);
	}

	@Transactional
	public Long addFavorite(User user, FavoriteRequest favoriteRequest) {
		Favorite favorite = new Favorite();
		Cocktail cocktail = cocktailRepository.findById(favoriteRequest.getCocktailId())
			.orElseThrow(() -> new EntityNotFoundException(ErrorCode.COCKTAIL_NOT_FOUND));

		favorite.setCocktail(cocktail);
		favorite.setUser(user);

		return favoriteRepository.save(favorite).getId();
	}

	@Transactional
	public void deleteFavorite(User user, Long cocktailId) {
		user.deleteFavorite(cocktailId);
		userRepository.save(user);
	}

	public FavoriteCocktailIdsResponse findFavoriteCocktailIds(User user) {
		List<Long> favoriteCocktailIds = user.findFavoriteCocktailIds();

		return new FavoriteCocktailIdsResponse(favoriteCocktailIds);
	}
}
