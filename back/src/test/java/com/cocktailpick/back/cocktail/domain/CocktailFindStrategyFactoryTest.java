package com.cocktailpick.back.cocktail.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CocktailFindStrategyFactoryTest {
	CocktailFindStrategyFactory cocktailFindStrategyFactory;

	@BeforeEach
	void setUp() {
		cocktailFindStrategyFactory = new CocktailFindStrategyFactory();
	}

	@Test
	void createCocktailOfDateStrategy() {
		CocktailSearcher cocktailOfDateStrategy = cocktailFindStrategyFactory.createCocktailSearcher(1234);
		assertThat(cocktailOfDateStrategy).isEqualTo(new CocktailSearcher(1234));
	}
}