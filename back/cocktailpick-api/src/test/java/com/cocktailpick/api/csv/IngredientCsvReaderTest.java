package com.cocktailpick.api.csv;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import com.cocktailpick.api.ingredient.Fixtures;
import com.cocktailpick.core.ingredient.dto.IngredientRequest;

class IngredientCsvReaderTest {
    private MultipartFile multipartFile;

    @BeforeEach
    void setUp() {
        String content = Fixtures.THREE_INGREDIENTS_CSV_CONTENT;
        multipartFile = new MockMultipartFile("file", "ingredient.csv", "text/csv", content.getBytes(StandardCharsets.UTF_8));
    }

    @Test
    void getIngredients() throws IOException {
        IngredientCsvReader ingredientCsvReader = new IngredientCsvReader(
            OpenCsvReader.from(multipartFile.getInputStream()));

        List<IngredientRequest> actual = ingredientCsvReader.getIngredientsRequests();

        assertAll(
            () -> assertThat(actual).hasSize(3),
            () -> assertThat(actual.get(0).getName()).isEqualTo("기네스"),
            () -> assertThat(actual.get(0).getAbv()).isEqualTo(4.2),
            () -> assertThat(actual.get(0).getColor()).isEqualTo("rgba")
        );
    }
}