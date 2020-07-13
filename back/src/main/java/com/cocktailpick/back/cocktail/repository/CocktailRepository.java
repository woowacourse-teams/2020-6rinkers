package com.cocktailpick.back.cocktail.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cocktailpick.back.cocktail.domain.Cocktail;

public interface CocktailRepository extends JpaRepository<Cocktail, Long> {
}
