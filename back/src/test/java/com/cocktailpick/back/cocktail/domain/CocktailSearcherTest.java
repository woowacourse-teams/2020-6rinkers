package com.cocktailpick.back.cocktail.domain;

import static org.assertj.core.api.Assertions.*;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CocktailSearcherTest {
	private CocktailSearcher cocktailSearcher;

	@BeforeEach
	void setUp() {
		cocktailSearcher = new CocktailSearcher(10);
	}

	@Test
	void findIn() {
		Cocktail first = Cocktail.builder().name("두강 진").build();
		Cocktail second = Cocktail.builder().name("토니 진").build();
		Cocktail third = Cocktail.builder().name("작곰 진").build();

		Cocktail cocktail = cocktailSearcher.findIn(Arrays.asList(first, second, third));

		assertThat(cocktail.getName()).isEqualTo("토니 진");
	}
}