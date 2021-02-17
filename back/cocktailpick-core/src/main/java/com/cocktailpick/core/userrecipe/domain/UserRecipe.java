package com.cocktailpick.core.userrecipe.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Embeddable
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
}
