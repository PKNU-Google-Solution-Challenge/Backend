package com.mealPrep.mealPrep.service;

import com.mealPrep.mealPrep.domain.Ingredient;
import com.mealPrep.mealPrep.domain.Recipe;
import com.mealPrep.mealPrep.domain.RecipeIngredient;
import com.mealPrep.mealPrep.dto.RecipeWriteRequestDTO;
import com.mealPrep.mealPrep.repository.IngredientRepository;
import com.mealPrep.mealPrep.repository.RecipeIngredientRepository;
import com.mealPrep.mealPrep.repository.RecipeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RecipeService {
    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private MemberService memberService;
    @Autowired
    private IngredientRepository ingredientRepository;
    @Autowired
    private RecipeIngredientRepository recipeIngredientRepository;

    @Transactional
    public Long createRecipe(RecipeWriteRequestDTO request) {
        // 레시피 엔티티 생성
        Recipe recipe = new Recipe();
        recipe.setCategory(request.getCategory());
        recipe.setPrice(request.getTotalPrice());
        recipe.setCooking_time(request.getTotalTime());
        recipe.setCalorie(request.getTotalKcal());

        // 레시피 엔티티 저장
        recipeRepository.save(recipe);

        // 재료 처리 및 RecipeIngredient 엔티티 생성
        List<RecipeIngredient> recipeIngredients = new ArrayList<>();
        for (String ingredientName : request.getIngredients()) {
            Ingredient ingredient = getOrCreateIngredient(ingredientName);

            RecipeIngredient recipeIngredient = new RecipeIngredient();
            recipeIngredient.setRecipe(recipe);
            recipeIngredient.setIngredient(ingredient);

            recipeIngredients.add(recipeIngredient);
        }

        // RecipeIngredient 엔티티 저장
        recipeIngredientRepository.saveAll(recipeIngredients);

        return recipe.getBoardId();
    }

    @Transactional
    public Ingredient getOrCreateIngredient(String ingredientName) {
        // 이름으로 기존 재료 찾기 시도
        Optional<Ingredient> existingIngredient = ingredientRepository.findByName(ingredientName);

        if (existingIngredient.isPresent()) {
            return existingIngredient.get();
        } else {
            // 찾지 못하면 새로운 재료 생성
            Ingredient newIngredient = new Ingredient();
            newIngredient.setName(ingredientName);

            // 새로운 재료 저장 후 반환
            return ingredientRepository.save(newIngredient);
        }
    }
}

