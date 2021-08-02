package com.cocktailpick.api.ingredient.controller;

import java.io.IOException;
import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cocktailpick.api.csv.IngredientCsvReader;
import com.cocktailpick.api.csv.OpenCsvReader;
import com.cocktailpick.core.ingredient.dto.IngredientRequest;
import com.cocktailpick.core.ingredient.dto.IngredientResponse;
import com.cocktailpick.core.ingredient.service.IngredientService;
import lombok.RequiredArgsConstructor;

@CrossOrigin("*")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/ingredients")
public class IngredientController {
    private final IngredientService ingredientService;

    @PostMapping
    public ResponseEntity<Void> createIngredient(@RequestBody IngredientRequest ingredientRequest) {
        Long saveId = ingredientService.save(ingredientRequest);
        return ResponseEntity.created(URI.create("/api/ingredients/" + saveId)).build();
    }

    @PostMapping("/upload/csv")
    public ResponseEntity<Void> addIngredientsByCsv(@RequestPart MultipartFile file) throws IOException {
        IngredientCsvReader ingredientCsvReader = new IngredientCsvReader(OpenCsvReader.from(file.getInputStream()));
        List<IngredientRequest> ingredientsRequests = ingredientCsvReader.getIngredientsRequests();
        ingredientService.saveAll(ingredientsRequests);
        return ResponseEntity.created(URI.create("/api/ingredients")).build();
    }

    @GetMapping
    public ResponseEntity<List<IngredientResponse>> findAll() {
        List<IngredientResponse> ingredientResponses = ingredientService.findAll();
        return ResponseEntity.ok(ingredientResponses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<IngredientResponse> findIngredient(@PathVariable Long id) {
        return ResponseEntity.ok(ingredientService.findIngredient(id));
    }

    @GetMapping("/name")
    public ResponseEntity<List<IngredientResponse>> findIngredientByName(@RequestParam(defaultValue = "") String name) {
        return ResponseEntity.ok(ingredientService.findByName(name));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateIngredient(@PathVariable Long id,
        @RequestBody IngredientRequest ingredientRequest) {
        ingredientService.update(id, ingredientRequest);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIngredient(@PathVariable Long id) {
        ingredientService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
