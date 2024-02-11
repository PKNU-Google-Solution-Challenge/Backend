package com.mealPrep.mealPrep.service;

import com.mealPrep.mealPrep.domain.Ingredient;
import com.mealPrep.mealPrep.domain.Recipe;
import com.mealPrep.mealPrep.domain.RecipeIngredient;
import com.mealPrep.mealPrep.dto.RecipeWriteRequestDTO;
import com.mealPrep.mealPrep.repository.IngredientRepository;
import com.mealPrep.mealPrep.repository.RecipeIngredientRepository;
import com.mealPrep.mealPrep.repository.RecipeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RecipeService {
    private final RecipeRepository recipeRepository;
    private final IngredientRepository ingredientRepository;
    private final RecipeIngredientRepository recipeIngredientRepository;
    @Transactional
    public Long createRecipe(RecipeWriteRequestDTO request){


        List<Ingredient> ingredients = new ArrayList<>();
        for (String s : request.getIngredients()) {
            Ingredient ingredient = new Ingredient().builder()
                    .name(s)
                    .build();
            ingredients.add(ingredient);
        }
        ingredientRepository.saveAll(ingredients);

        Recipe recipe = new Recipe().builder()
                .category(request.getCategory())
                .price(request.getTotalPrice())
                .calorie(request.getTotalKcal())
                .cooking_time(request.getTotalTime())
                .build();
        recipeRepository.save(recipe);

//        recipeIngredientRepository.save(RecipeIngredient.builder().recipe(recipe).ingredient(ingredients.get(0)).build());
        // RecipeIngredient 엔티티 생성 및 저장
        for (Ingredient ingredient : ingredients) {
            RecipeIngredient recipeIngredient = new RecipeIngredient();
            recipeIngredient.setRecipe(recipe);
            recipeIngredient.setIngredient(ingredient);
            recipeIngredientRepository.save(recipeIngredient);
        }
        return recipe.getBoardId();
    }
}
