package com.mealPrep.mealPrep.repository;

import com.mealPrep.mealPrep.domain.Recipe;
import com.mealPrep.mealPrep.domain.RecipeIngredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeIngredientRepository extends JpaRepository<RecipeIngredient,Long> {
    List<RecipeIngredient> findAllByRecipe_BoardId(Long recipeId);
}
