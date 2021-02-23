package com.cocktailpick.core.usercocktail.domain;

import java.util.List;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.cocktailpick.core.common.domain.BaseTimeEntity;
import com.cocktailpick.core.userrecipe.domain.UserRecipe;
import com.cocktailpick.core.userrecipe.domain.UserRecipeItem;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class UserCocktail extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_cocktail_sequence_gen")
    @SequenceGenerator(
        name = "user_cocktail_sequence_gen",
        sequenceName = "user_cocktail_sequence"
    )
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @NotNull
    private Long memberId;

    @Embedded
    private UserRecipe userRecipe = UserRecipe.empty();

    @Builder
    public UserCocktail(Long id, String name, String description, Long memberId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.memberId = memberId;
    }

    public void update(UserCocktail updateUserCocktail, List<UserRecipeItem> userRecipeItems) {
        this.name = updateUserCocktail.name;
        this.description = updateUserCocktail.description;

        updateUserRecipe(userRecipeItems);
    }

    private void updateUserRecipe(List<UserRecipeItem> userRecipeItems) {
        this.userRecipe.clear();
        for (UserRecipeItem userRecipeItem : userRecipeItems) {
            userRecipeItem.setUserCocktail(this);
        }
    }
}
