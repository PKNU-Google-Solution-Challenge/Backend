package com.mealPrep.mealPrep.repository;

import com.mealPrep.mealPrep.domain.RecipeIngredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeIngredientRepository extends JpaRepository<RecipeIngredient,Long> {
}