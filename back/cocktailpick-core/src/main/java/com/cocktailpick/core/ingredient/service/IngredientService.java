package com.cocktailpick.core.ingredient.service;

import com.cocktailpick.core.ingredient.domain.Ingredient;
import com.cocktailpick.core.ingredient.domain.IngredientRepository;
import com.cocktailpick.core.ingredient.dto.IngredientCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class IngredientService {
    private final IngredientRepository ingredientRepository;

    @Transactional
    public Long save(IngredientCreateRequest ingredientCreateRequest) {
        Ingredient ingredient = ingredientCreateRequest.toIngredient();
        Ingredient savedIngredient = ingredientRepository.save(ingredient);

        return savedIngredient.getId();
    }

}
