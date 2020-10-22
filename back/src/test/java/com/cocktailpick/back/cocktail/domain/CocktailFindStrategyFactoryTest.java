package com.cocktailpick.back.cocktail.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CocktailFindStrategyFactoryTest {
	CocktailFindStrategyFactory cocktailFindStrategyFactory;

	@BeforeEach
	void setUp() {
		cocktailFindStrategyFactory = new CocktailFindStrategyFactory();
	}

	@DisplayName("입력한 seed 값이 정확히 들어가는 지 확인한다.")
	@Test
	void createCocktailOfDateStrategy() {
		long seed = 1234L;
		CocktailSearcher cocktailOfDateStrategy = cocktailFindStrategyFactory.createCocktailSearcher(seed);

		assertThat(cocktailOfDateStrategy).extracting("seed").isEqualTo(seed);
	}
}