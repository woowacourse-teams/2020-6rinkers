package com.cocktailpick.back.cocktail.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cocktailpick.back.cocktail.dto.CocktailResponse;
import com.cocktailpick.back.cocktail.service.CocktailService;

@RestController
@RequestMapping("/api/cocktails")
public class CocktailController {
	private final CocktailService cocktailService;

	public CocktailController(CocktailService cocktailService) {
		this.cocktailService = cocktailService;
	}

	@GetMapping
	public ResponseEntity<List<CocktailResponse>> findCocktails() {
		return ResponseEntity.ok(cocktailService.findAllCocktails());
	}
}
