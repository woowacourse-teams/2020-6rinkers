package com.cocktailpick.core.usercocktail.service;

import static java.util.stream.Collectors.*;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cocktailpick.core.common.exceptions.AuthException;
import com.cocktailpick.core.common.exceptions.EntityNotFoundException;
import com.cocktailpick.core.common.exceptions.ErrorCode;
import com.cocktailpick.core.ingredient.domain.Ingredient;
import com.cocktailpick.core.ingredient.domain.IngredientRepository;
import com.cocktailpick.core.user.domain.User;
import com.cocktailpick.core.usercocktail.domain.UserCocktail;
import com.cocktailpick.core.usercocktail.domain.UserCocktailRepository;
import com.cocktailpick.core.usercocktail.dto.UserCocktailRequest;
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
    public Long save(User user, UserCocktailRequest userCocktailRequest) {
        UserCocktail userCocktail = userCocktailRequest.toUserCocktail(user.getId());
        userCocktailRepository.save(userCocktail);

        List<Long> ingredientIds = userCocktailRequest.getUserRecipeItemRequests()
            .stream()
            .map(UserRecipeItemRequest::getIngredientId)
            .collect(Collectors.toList());

        List<Ingredient> ingredients = ingredientRepository.findAllById(ingredientIds);
        List<UserRecipeItem> userRecipeItems = userCocktailRequest.toUserRecipeItems(ingredients);

        setUserCocktail(userCocktail, userRecipeItems);

        return userCocktail.getId();
    }

    private void setUserCocktail(UserCocktail userCocktail, List<UserRecipeItem> userRecipeItems) {
        for (UserRecipeItem userRecipeItem : userRecipeItems) {
            userRecipeItem.setUserCocktail(userCocktail);
        }
    }

    @Transactional(readOnly = true)
    public UserCocktailResponse findUserCocktail(Long id) {
        UserCocktail userCocktail = findUserCocktailById(id);
        return UserCocktailResponse.of(userCocktail);
    }

    private UserCocktail findUserCocktailById(Long id) {
        return userCocktailRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException(ErrorCode.USERCOCKTAIL_NOT_FOUND));
    }

    @Transactional(readOnly = true)
    public UserCocktailResponses findUserCocktails() {
        List<UserCocktail> userCocktails = userCocktailRepository.findAll();

        return userCocktails.stream()
            .map(UserCocktailResponse::of)
            .collect(collectingAndThen(toList(), UserCocktailResponses::new));
    }

    @Transactional
    public void updateUserCocktail(User user, Long id, UserCocktailRequest updateUserCocktailRequest) {
        UserCocktail userCocktail = findUserCocktailById(id);

        if (user.isNotPossibleToUpdateUserCocktail(userCocktail.getMemberId())) {
            throw new AuthException(ErrorCode.USERCOCKTAIL_UNAUTHORIZED);
        }

        UserCocktail updateUserCocktail = updateUserCocktailRequest.toUserCocktail(user.getId());
        List<Long> ingredientIds = updateUserCocktailRequest.getUserRecipeItemRequests()
            .stream()
            .map(UserRecipeItemRequest::getIngredientId)
            .collect(Collectors.toList());

        List<Ingredient> ingredients = ingredientRepository.findAllById(ingredientIds);
        List<UserRecipeItem> userRecipeItems = updateUserCocktailRequest.toUserRecipeItems(ingredients);

        userCocktail.update(updateUserCocktail, userRecipeItems);
    }
}
