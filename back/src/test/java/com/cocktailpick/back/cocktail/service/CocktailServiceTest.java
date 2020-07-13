package com.cocktailpick.back.cocktail.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cocktailpick.back.cocktail.domain.Cocktail;
import com.cocktailpick.back.cocktail.dto.CocktailResponse;
import com.cocktailpick.back.cocktail.repository.CocktailRepository;

@ExtendWith(MockitoExtension.class)
class CocktailServiceTest {
	private CocktailService cocktailService;

	@Mock
	private CocktailRepository cocktailRepository;

	@BeforeEach
	void setUp() {
		cocktailService = new CocktailService(cocktailRepository);
	}

	@DisplayName("모든 칵테일을 조회한다.")
	@Test
	void findAllCocktails() {
		Cocktail blueHawaii = Cocktail.builder()
			.name("블루 하와이")
			.build();

		Cocktail martini = Cocktail.builder()
			.name("마티니")
			.build();

		when(cocktailRepository.findAll()).thenReturn(Arrays.asList(blueHawaii, martini));

		List<CocktailResponse> cocktails = cocktailService.findAllCocktails();

		assertThat(cocktails).extracting("name")
			.containsExactly(blueHawaii.getName(), martini.getName());
	}
}