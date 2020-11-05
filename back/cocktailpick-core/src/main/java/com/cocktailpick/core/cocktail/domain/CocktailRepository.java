package com.cocktailpick.core.cocktail.domain;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CocktailRepository extends JpaRepository<Cocktail, Long> {
	@Query("select c from Cocktail c left join fetch c.cocktailTags where c.id > :id and c.name like %:contain%")
	Page<Cocktail> findByNameContainingAndIdGreaterThanWithCocktailTags(String contain, long id, Pageable pageRequest);

	@Query("select c from Cocktail c left join fetch c.cocktailTags where c.name like %:contain%")
	List<Cocktail> findByNameContainingWithCocktailTags(String contain);

	@Query("select c from Cocktail c left join fetch c.cocktailTags where c.id > :id")
	List<Cocktail> findByIdGreaterThanWithCocktailTags(long id);

	@Query("select c from Cocktail c left join fetch c.cocktailTags left join fetch c.recipe")
	List<Cocktail> findAllWithCocktailTagsAndRecipe();
}
