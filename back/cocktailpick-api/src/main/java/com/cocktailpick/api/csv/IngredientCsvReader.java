package com.cocktailpick.api.csv;

import java.util.List;
import java.util.stream.Collectors;

import com.cocktailpick.core.ingredient.dto.IngredientRequest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class IngredientCsvReader {
    private static final int NAME_COLUMN_INDEX = 0;
    private static final int ABV_COLUMN_INDEX = 1;
    private static final int COLOR_COLUMN_INDEX = 2;

    private final CsvReader csvReader;

    public List<IngredientRequest> getIngredientsRequests() {
        List<String[]> ingredients = csvReader.readAll();
        return ingredients.stream()
            .map(line -> IngredientRequest.builder()
                .name(line[NAME_COLUMN_INDEX].trim())
                .abv(Double.parseDouble(line[ABV_COLUMN_INDEX].trim()))
                .color(line[COLOR_COLUMN_INDEX])
                .build())
            .collect(Collectors.toList());
    }
}
