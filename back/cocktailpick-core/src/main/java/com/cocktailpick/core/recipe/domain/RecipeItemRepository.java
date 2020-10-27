package com.cocktailpick.core.recipe.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeItemRepository extends JpaRepository<RecipeItem, Long> {
}
