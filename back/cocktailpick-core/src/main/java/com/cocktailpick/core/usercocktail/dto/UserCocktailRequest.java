package com.cocktailpick.core.usercocktail.dto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;

import com.cocktailpick.core.ingredient.domain.Ingredient;
import com.cocktailpick.core.usercocktail.domain.UserCocktail;
import com.cocktailpick.core.userrecipe.domain.UserRecipeItem;
import com.cocktailpick.core.userrecipe.dto.UserRecipeItemRequest;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class UserCocktailRequest {
    @NotBlank
    private String name;
    @NotBlank
    private String description;

    private List<UserRecipeItemRequest> userRecipeItemRequests;

    @Builder
    public UserCocktailRequest(@NotBlank String name, @NotBlank String description,
        List<UserRecipeItemRequest> userRecipeItemRequests) {
        this.name = name;
        this.description = description;
        this.userRecipeItemRequests = userRecipeItemRequests;
    }

    public UserCocktail toUserCocktail(Long userId) {
        return UserCocktail.builder()
                .name(name)
                .description(description)
                .memberId(userId)
                .build();
    }

    public List<UserRecipeItem> toUserRecipeItems(List<Ingredient> ingredients) {
        List<UserRecipeItem> userRecipeItems = new ArrayList<>();
        for (int i = 0; i < ingredients.size(); i++) {
            UserRecipeItem userRecipeItem = userRecipeItemRequests.get(i).toUserRecipeItem(ingredients.get(i));
            userRecipeItems.add(userRecipeItem);
        }
        return userRecipeItems;
    }
}
