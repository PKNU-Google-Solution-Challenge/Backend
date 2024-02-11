package com.mealPrep.mealPrep.repository;

import com.mealPrep.mealPrep.domain.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeRepository extends JpaRepository<Recipe,Long> {

}

