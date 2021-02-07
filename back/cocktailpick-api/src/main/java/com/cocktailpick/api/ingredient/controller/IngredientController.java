package com.cocktailpick.api.ingredient.controller;

import com.cocktailpick.core.ingredient.dto.IngredientCreateRequest;
import com.cocktailpick.core.ingredient.dto.IngredientResponse;
import com.cocktailpick.core.ingredient.service.IngredientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@CrossOrigin("*")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/ingredients")
public class IngredientController {
    private final IngredientService ingredientService;

    @PostMapping
    public ResponseEntity<Void> createIngredient(@RequestBody IngredientCreateRequest ingredientCreateRequest) {
       Long saveId = ingredientService.save(ingredientCreateRequest);
       return ResponseEntity.created(URI.create("/api/ingredients/" + saveId)).build();
    }

    @GetMapping
    public ResponseEntity<List<IngredientResponse>> findAll() {
        List<IngredientResponse> ingredientResponses = ingredientService.findAll();
        return ResponseEntity.ok(ingredientResponses);
    }
}
