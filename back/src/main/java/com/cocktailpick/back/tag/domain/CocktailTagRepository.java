package com.cocktailpick.back.tag.domain;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CocktailTagRepository extends JpaRepository<CocktailTag, Long> {
	List<CocktailTag> findByTagId(Long id);
}
