package com.mealPrep.mealPrep.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@RequiredArgsConstructor
@Setter
@Getter
public class RecipeIngredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="recipe_id")
    private Recipe recipe;

    @ManyToOne
    @JoinColumn(name="ingredient_id")
    private Ingredient ingredient;
}

