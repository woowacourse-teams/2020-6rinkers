package com.cocktailpick.core.usercocktail.service;

import static java.util.stream.Collectors.*;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cocktailpick.core.common.exceptions.EntityNotFoundException;
import com.cocktailpick.core.common.exceptions.ErrorCode;
import com.cocktailpick.core.ingredient.domain.Ingredient;
import com.cocktailpick.core.ingredient.domain.IngredientRepository;
import com.cocktailpick.core.user.domain.User;
import com.cocktailpick.core.usercocktail.domain.UserCocktail;
import com.cocktailpick.core.usercocktail.domain.UserCocktailRepository;
import com.cocktailpick.core.usercocktail.dto.UserCocktailCreateRequest;
import com.cocktailpick.core.usercocktail.dto.UserCocktailResponse;
import com.cocktailpick.core.usercocktail.dto.UserCocktailResponses;
import com.cocktailpick.core.userrecipe.domain.UserRecipeItem;
import com.cocktailpick.core.userrecipe.dto.UserRecipeItemRequest;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
@Service
public class UserCocktailService {
    private final UserCocktailRepository userCocktailRepository;
    private final IngredientRepository ingredientRepository;

    @Transactional
    public Long save(User user, UserCocktailCreateRequest userCocktailCreateRequest) {
        UserCocktail userCocktail = userCocktailCreateRequest.toUserCocktail(user.getId());
        userCocktailRepository.save(userCocktail);

        List<Long> ingredientIds = userCocktailCreateRequest.getUserRecipeItemRequests()
                .stream()
                .map(UserRecipeItemRequest::getIngredientId)
                .collect(Collectors.toList());

        List<Ingredient> ingredients = ingredientRepository.findAllById(ingredientIds);
        List<UserRecipeItem> userRecipeItems = userCocktailCreateRequest.toUserRecipeItems(ingredients);

        setUserCocktail(userCocktail, userRecipeItems);

        return userCocktail.getId();
    }

    private void setUserCocktail(UserCocktail userCocktail, List<UserRecipeItem> userRecipeItems) {
        for (UserRecipeItem userRecipeItem : userRecipeItems) {
            userRecipeItem.setUserCocktail(userCocktail);
        }
    }

    public UserCocktailResponse findUserCocktail(Long id) {
        UserCocktail userCocktail = findUserCocktailById(id);
        return UserCocktailResponse.of(userCocktail);
    }

    private UserCocktail findUserCocktailById(Long id) {
        return userCocktailRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException(ErrorCode.USERCOCKTAIL_NOT_FOUND));
    }

    public UserCocktailResponses findUserCocktails() {
        List<UserCocktail> userCocktails = userCocktailRepository.findAll();

        return userCocktails.stream()
            .map(UserCocktailResponse::of)
            .collect(collectingAndThen(toList(), UserCocktailResponses::new));
    }
}
