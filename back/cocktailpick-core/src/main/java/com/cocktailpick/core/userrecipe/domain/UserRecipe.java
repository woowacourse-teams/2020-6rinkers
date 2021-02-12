package com.cocktailpick.core.userrecipe.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.OneToMany;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class UserRecipe {
    @OneToMany(mappedBy = "userCocktail", cascade = CascadeType.PERSIST)
    private List<UserRecipeItem> userRecipe = new ArrayList<>();

    public static UserRecipe empty() {
        return new UserRecipe();
    }
}
