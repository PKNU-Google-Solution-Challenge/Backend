package com.mealPrep.mealPrep.repository;

import com.mealPrep.mealPrep.domain.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe,Long> {

    List<Recipe> findAllByOrderByCreatedAtDesc();
}

