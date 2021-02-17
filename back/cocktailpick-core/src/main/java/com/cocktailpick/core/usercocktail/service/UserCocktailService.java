package com.cocktailpick.core.usercocktail.service;

import com.cocktailpick.core.ingredient.domain.Ingredient;
import com.cocktailpick.core.ingredient.domain.IngredientRepository;
import com.cocktailpick.core.user.domain.User;
import com.cocktailpick.core.usercocktail.domain.UserCocktail;
import com.cocktailpick.core.usercocktail.domain.UserCocktailRepository;
import com.cocktailpick.core.usercocktail.dto.UserCocktailCreateRequest;
import com.cocktailpick.core.userrecipe.domain.UserRecipeItem;
import com.cocktailpick.core.userrecipe.dto.UserRecipeItemRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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
}
