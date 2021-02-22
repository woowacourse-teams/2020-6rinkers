package com.cocktailpick.api.userCocktail.controller;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cocktailpick.api.security.CurrentUser;
import com.cocktailpick.core.user.domain.User;
import com.cocktailpick.core.usercocktail.dto.UserCocktailCreateRequest;
import com.cocktailpick.core.usercocktail.dto.UserCocktailResponse;
import com.cocktailpick.core.usercocktail.dto.UserCocktailResponses;
import com.cocktailpick.core.usercocktail.service.UserCocktailService;

import lombok.RequiredArgsConstructor;

@CrossOrigin("*")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user-cocktails")
public class UserCocktailController {
    private final UserCocktailService userCocktailService;

    @PostMapping
    public ResponseEntity<Void> createUserCocktail(@CurrentUser User user,
        @RequestBody UserCocktailCreateRequest userCocktailCreateRequest) {
        Long saveId = userCocktailService.save(user, userCocktailCreateRequest);
        return ResponseEntity.created(URI.create("/api/user-cocktails/" + saveId)).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserCocktailResponse> findUserCocktail(@PathVariable Long id) {
        return ResponseEntity.ok(userCocktailService.findUserCocktail(id));
    }

    @GetMapping
    public ResponseEntity<UserCocktailResponses> findUserCocktails() {
        return ResponseEntity.ok(userCocktailService.findUserCocktails());
    }
}
