package com.cocktailpick.core.usercocktail.domain;

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
import lombok.AccessLevel;
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
    private String id;

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @NotNull
    private Long memberId;

    @Embedded
    private UserRecipe userRecipe = UserRecipe.empty();
}
