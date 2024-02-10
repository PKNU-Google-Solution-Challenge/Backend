package com.mealPrep.mealPrep.domain;

import jakarta.persistence.*;

@Entity
public class RecipeIngredient {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name="recipe_id")
    private Recipe recipe;

    @ManyToOne
    @JoinColumn(name="ingredient_id")
    private Ingredient ingredient;
}
