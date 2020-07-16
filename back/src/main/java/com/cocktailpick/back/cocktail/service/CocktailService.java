package com.cocktailpick.back.cocktail.service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cocktailpick.back.cocktail.domain.Cocktail;
import com.cocktailpick.back.cocktail.domain.CocktailRepository;
import com.cocktailpick.back.cocktail.dto.CocktailDetailResponse;
import com.cocktailpick.back.cocktail.dto.CocktailRequest;
import com.cocktailpick.back.cocktail.dto.CocktailResponse;

@Service
public class CocktailService {
	private final CocktailRepository cocktailRepository;

	public CocktailService(CocktailRepository cocktailRepository) {
		this.cocktailRepository = cocktailRepository;
	}

	@Transactional(readOnly = true)
	public List<CocktailResponse> findAllCocktails() {
		return Collections.unmodifiableList(cocktailRepository.findAll().stream()
			.map(CocktailResponse::of)
			.collect(Collectors.toList()));
	}

	@Transactional(readOnly = true)
	public CocktailDetailResponse findCocktail(Long id) {
		Cocktail cocktail = cocktailRepository.findById(id)
			.orElseThrow(RuntimeException::new);

		return CocktailDetailResponse.of(cocktail);
	}
}
