package com.cocktailpick.core.ingredient.service;

import com.cocktailpick.core.common.exceptions.EntityNotFoundException;
import com.cocktailpick.core.common.exceptions.ErrorCode;
import com.cocktailpick.core.ingredient.domain.Ingredient;
import com.cocktailpick.core.ingredient.domain.IngredientRepository;
import com.cocktailpick.core.ingredient.dto.IngredientRequest;
import com.cocktailpick.core.ingredient.dto.IngredientResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;


@RequiredArgsConstructor
@Service
public class IngredientService {
    private final IngredientRepository ingredientRepository;

    @Transactional
    public Long save(IngredientRequest ingredientRequest) {
        Ingredient ingredient = ingredientRequest.toIngredient();
        Ingredient savedIngredient = ingredientRepository.save(ingredient);

        return savedIngredient.getId();
    }

    @Cacheable(value = "ingredients")
    @Transactional(readOnly = true)
    public List<IngredientResponse> findAll() {
        List<Ingredient> ingredients = ingredientRepository.findAll();

        return Collections.unmodifiableList(IngredientResponse.listOf(ingredients));
    }

    @Cacheable(value = "ingredient", key = "#id")
    @Transactional(readOnly = true)
    public IngredientResponse findIngredient(Long id) {
        Ingredient ingredient = findById(id);
        return IngredientResponse.of(ingredient);
    }

    private Ingredient findById(Long id) {
        return ingredientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.INGREDIENT_NOT_FOUND));
    }

    @CachePut(value = "ingredient", key = "#id")
    @Transactional
    public void updateIngredient(Long id, IngredientRequest ingredientRequest) {
        Ingredient ingredient = findById(id);
        Ingredient requestIngredient = ingredientRequest.toIngredient();

        ingredient.update(requestIngredient);
    }

    @CacheEvict(value = "ingredient", key = "#id")
    @Transactional
    public void deleteIngredient(Long id) {
        ingredientRepository.deleteById(id);
    }
}
