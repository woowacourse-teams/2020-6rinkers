package com.cocktailpick.core.usercocktail.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Collections;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cocktailpick.core.ingredient.domain.Ingredient;
import com.cocktailpick.core.ingredient.domain.IngredientRepository;
import com.cocktailpick.core.user.domain.User;
import com.cocktailpick.core.usercocktail.domain.UserCocktail;
import com.cocktailpick.core.usercocktail.domain.UserCocktailRepository;
import com.cocktailpick.core.usercocktail.dto.UserCocktailRequest;
import com.cocktailpick.core.userrecipe.dto.UserRecipeItemRequest;

@ExtendWith(MockitoExtension.class)
class UserCocktailServiceTest {
	@Mock
	private UserCocktailRepository userCocktailRepository;
	@Mock
	private IngredientRepository ingredientRepository;

	private UserCocktailService userCocktailService;

	private Ingredient ingredient;

	private UserCocktail userCocktail;

	private User user;

	private UserCocktailRequest userCocktailRequest;

	private UserRecipeItemRequest userRecipeItemRequest;

	@BeforeEach
	void setUp() {
		userCocktailService = new UserCocktailService(userCocktailRepository, ingredientRepository);
		ingredient = Ingredient.builder()
			.id(1L)
			.name("test")
			.abv(1.2)
			.build();

		userCocktail = UserCocktail.builder()
			.id(1L)
			.name("testCocktail")
			.description("this is service test")
			.memberId(1L)
			.build();

		user = User.builder()
			.id(1L)
			.name("test user")
			.email("test@test.com")
			.build();

		userRecipeItemRequest = UserRecipeItemRequest.builder()
			.ingredientId(1L)
			.quantity(23.0)
			.quantityUnit("SOJU")
			.build();
		userCocktailRequest = UserCocktailRequest.builder()
			.name("testRequest")
			.description("description")
			.userRecipeItemRequests(Collections.singletonList(userRecipeItemRequest))
			.build();
	}

	@DisplayName("나만의 레시피를 생성한다.")
	@Test
	void save() {
		lenient().when(ingredientRepository.findAllById(any())).thenReturn(Collections.singletonList(ingredient));

		userCocktailService.save(user, userCocktailRequest);

		verify(userCocktailRepository).save(any());
	}

	@DisplayName("나만의 레시피 단건 조회한다.")
	@Test
	void findUserCocktail() {
		when(userCocktailRepository.findById(anyLong())).thenReturn(Optional.ofNullable(userCocktail));

		assertThat(userCocktailService.findUserCocktail(1L).getName()).isEqualTo("testCocktail");
	}

	@DisplayName("나만의 레시피 전체 조회한다.")
	@Test
	void findUserCocktails() {
		when(userCocktailRepository.findAll()).thenReturn(Collections.singletonList(userCocktail));

		assertThat(userCocktailService.findUserCocktails()
			.getUserCocktailResponses()).hasSize(1);
	}

	@DisplayName("나만의 레시피를 수정한다.")
	@Test
	void updateUserCocktail() {
		when(userCocktailRepository.findById(anyLong())).thenReturn(Optional.ofNullable(userCocktail));
		lenient().when(ingredientRepository.findAllById(any())).thenReturn(Collections.singletonList(ingredient));

		userCocktailService.updateUserCocktail(user, 1L, userCocktailRequest);

		assertThat(userCocktail.getName()).isEqualTo("testRequest");
	}

	@DisplayName("나만의 레시피를 삭제한다.")
	@Test
	void deleteUserCocktail() {
		when(userCocktailRepository.findById(anyLong())).thenReturn(Optional.ofNullable(userCocktail));

		userCocktailService.deleteUserCocktail(user, 1L);

		verify(userCocktailRepository).deleteById(1L);
	}
}