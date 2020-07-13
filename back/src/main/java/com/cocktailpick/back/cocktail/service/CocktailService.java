package com.cocktailpick.back.cocktail.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.cocktailpick.back.cocktail.dto.CocktailResponse;
import com.cocktailpick.back.cocktail.repository.CocktailRepository;

@Service
public class CocktailService {
	private final CocktailRepository cocktailRepository;

	public CocktailService(CocktailRepository cocktailRepository) {
		this.cocktailRepository = cocktailRepository;
	}

	public List<CocktailResponse> findAllCocktails() {
		return cocktailRepository.findAll().stream()
			.map(CocktailResponse::of)
			.collect(Collectors.toList());
	}
}
