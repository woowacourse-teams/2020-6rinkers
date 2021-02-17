package com.cocktailpick.api.userCocktail.controller;

import com.cocktailpick.api.security.CurrentUser;
import com.cocktailpick.core.user.domain.User;
import com.cocktailpick.core.usercocktail.dto.UserCocktailCreateRequest;
import com.cocktailpick.core.usercocktail.service.UserCocktailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@CrossOrigin("*")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user-cocktails")
public class UserCocktailController {
    private final UserCocktailService userCocktailService;

    @PostMapping
    public ResponseEntity<Void> createUserCocktail(@CurrentUser User user, @RequestBody UserCocktailCreateRequest userCocktailCreateRequest) {
        Long saveId = userCocktailService.save(user, userCocktailCreateRequest);
        return ResponseEntity.created(URI.create("/api/user-cocktails/" + saveId)).build();
    }
}
