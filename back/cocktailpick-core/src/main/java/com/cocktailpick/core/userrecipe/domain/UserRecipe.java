package com.cocktailpick.core.userrecipe.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.OneToMany;

import com.cocktailpick.core.ingredient.domain.Ingredient;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class UserRecipe {
    @OneToMany(mappedBy = "userCocktail", cascade = CascadeType.PERSIST)
    private List<UserRecipeItem> userRecipe = new ArrayList<>();

    public static UserRecipe empty() {
        return new UserRecipe();
    }

    public void addUserRecipeItem(UserRecipeItem userRecipeItem) {
        userRecipe.add(userRecipeItem);
    }

    public List<Ingredient> getIngredients() {
        return userRecipe.stream()
            .map(UserRecipeItem::getIngredient)
            .collect(Collectors.toList());
    }
}
