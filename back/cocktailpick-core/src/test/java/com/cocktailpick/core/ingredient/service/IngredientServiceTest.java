package com.cocktailpick.core.ingredient.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cocktailpick.core.ingredient.domain.Ingredient;
import com.cocktailpick.core.ingredient.domain.IngredientRepository;
import com.cocktailpick.core.ingredient.dto.IngredientRequest;
import com.cocktailpick.core.ingredient.dto.IngredientResponse;

@ExtendWith(MockitoExtension.class)
class IngredientServiceTest {
    private IngredientService ingredientService;

    @Mock
    private IngredientRepository ingredientRepository;

    private IngredientRequest createRequest;

    private Ingredient ingredient;

    @BeforeEach
    void setUp() {
        ingredientService = new IngredientService(ingredientRepository);

        createRequest = IngredientRequest.builder()
            .name("소주")
            .color("#000000")
            .abv(17.6)
            .build();

        ingredient = Ingredient.builder()
            .id(1L)
            .name("소주")
            .color("#000000")
            .abv(17.6)
            .build();
    }

    @DisplayName("재료를 생성한다.")
    @Test
    void save() {
        when(ingredientRepository.save(any())).thenReturn(ingredient);

        ingredientService.save(createRequest);

        verify(ingredientRepository).save(any());
    }

    @DisplayName("모든 재료를 조회한다.")
    @Test
    void findAll() {
        Ingredient beer = Ingredient.builder()
            .id(2L)
            .name("맥주")
            .color("#222222")
            .abv(5.0)
            .build();

        when(ingredientRepository.findAll()).thenReturn(Arrays.asList(ingredient, beer));

        List<IngredientResponse> ingredients = ingredientService.findAll();

        assertThat(ingredients).extracting("name")
            .containsExactly(ingredient.getName(), beer.getName());
    }

    @DisplayName("id로 재료를 조회한다.")
    @Test
    void findIngredient() {
        when(ingredientRepository.findById(any())).thenReturn(Optional.of(ingredient));

        IngredientResponse response = ingredientService.findIngredient(1L);

        assertAll(
            () -> assertThat(response.getId()).isEqualTo(ingredient.getId()),
            () -> assertThat(response.getName()).isEqualTo(ingredient.getName()),
            () -> assertThat(response.getColor()).isEqualTo(ingredient.getColor()),
            () -> assertThat(response.getAbv()).isEqualTo(ingredient.getAbv())
        );
    }

    @Test
    void update() {
        IngredientRequest updateRequest = IngredientRequest.builder()
            .name(createRequest.getName())
            .color(createRequest.getColor())
            .abv(16.5)
            .build();

        when(ingredientRepository.findById(any())).thenReturn(Optional.of(ingredient));

        ingredientService.update(ingredient.getId(), updateRequest);

        assertAll(
            () -> assertThat(ingredient.getId()).isEqualTo(1L),
            () -> assertThat(ingredient.getName()).isEqualTo(updateRequest.getName()),
            () -> assertThat(ingredient.getColor()).isEqualTo(updateRequest.getColor()),
            () -> assertThat(ingredient.getAbv()).isEqualTo(updateRequest.getAbv())
        );
    }

    @DisplayName("재료를 삭제한다.")
    @Test
    void delete() {
        ingredientService.delete(1L);

        verify(ingredientRepository).deleteById(1L);
    }
}