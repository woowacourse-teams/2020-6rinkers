package com.cocktailpick.core.ingredient.domain;

import com.cocktailpick.core.common.domain.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
@Entity
public class Ingredient extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ingredient_sequence")
    @SequenceGenerator(
            name = "ingredient_sequence_gen",
            sequenceName = "ingredient_sequence"
    )
    private Long id;
    @NotBlank
    private String title;
    @NotBlank
    private String color;
    @NotNull
    private Double abv;

    @Builder
    public Ingredient(Long id, @NotBlank String title, @NotBlank String color, @NotNull Double abv) {
        this.id = id;
        this.title = title;
        this.color = color;
        this.abv = abv;
    }

    public void update(Ingredient requestIngredient) {
        this.title = requestIngredient.title;
        this.color = requestIngredient.color;
        this.abv = requestIngredient.abv;
    }
}
