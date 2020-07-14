package com.cocktailpick.back.cocktail.service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cocktailpick.back.cocktail.dto.CocktailResponse;
import com.cocktailpick.back.cocktail.repository.CocktailRepository;

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
}
