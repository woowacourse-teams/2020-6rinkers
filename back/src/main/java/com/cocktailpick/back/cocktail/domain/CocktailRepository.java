package com.cocktailpick.back.cocktail.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CocktailRepository extends JpaRepository<Cocktail, Long> {
	Page<Cocktail> findByIdGreaterThan(long id, Pageable pageRequest);
}
