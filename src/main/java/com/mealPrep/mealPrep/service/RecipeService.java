package com.mealPrep.mealPrep.service;

import com.mealPrep.mealPrep.domain.Recipe;
import com.mealPrep.mealPrep.dto.RecipeWriteRequestDTO;
import com.mealPrep.mealPrep.repository.RecipeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RecipeService {
    private final RecipeRepository recipeRepository;

    @Transactional
    public Long createRecipe(RecipeWriteRequestDTO request){
        Recipe recipe = new Recipe(request);
        recipeRepository.save(recipe);
        return recipe.getBoardId();
    }
}
