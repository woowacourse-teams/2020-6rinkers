package com.cocktailpick.core.ingredient.domain;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cocktailpick.core.cocktail.domain.Cocktail;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
	List<Ingredient> findByNameContaining(String name);
}
