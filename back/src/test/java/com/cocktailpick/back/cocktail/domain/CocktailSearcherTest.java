package com.cocktailpick.back.cocktail.domain;

import static org.assertj.core.api.Assertions.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.cocktailpick.back.common.exceptions.EntityNotFoundException;

class CocktailSearcherTest {
	private CocktailSearcher cocktailSearcher;

	@BeforeEach
	void setUp() {
		cocktailSearcher = new CocktailSearcher(10);
	}

	@DisplayName("시드 값에 따라 적절한 Cocktail이 뽑힌다.")
	@Test
	void findIn() {
		Cocktail first = Cocktail.builder().name("두강 진").build();
		Cocktail second = Cocktail.builder().name("토니 진").build();
		Cocktail third = Cocktail.builder().name("작곰 진").build();

		Cocktail cocktail = cocktailSearcher.findIn(Arrays.asList(first, second, third));

		assertThat(cocktail.getName()).isEqualTo("토니 진");
	}

	@DisplayName("입력된 칵테일 목록이 빌 경우 예외 처리한다.")
	@Test
	void findInException() {
		List<Cocktail> cocktails = Collections.emptyList();

		assertThatThrownBy(() -> cocktailSearcher.findIn(cocktails))
			.isInstanceOf(EntityNotFoundException.class);
	}
}