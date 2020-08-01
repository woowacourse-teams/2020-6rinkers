package com.cocktailpick.back.cocktail.domain;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CocktailRepository extends JpaRepository<Cocktail, Long> {
	Page<Cocktail> findByIdGreaterThan(long id, Pageable pageRequest);

	List<Cocktail> findByNameContaining(String name);
}
