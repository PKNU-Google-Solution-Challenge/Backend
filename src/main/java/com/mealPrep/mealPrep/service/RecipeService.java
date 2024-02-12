package com.mealPrep.mealPrep.service;

import com.mealPrep.mealPrep.domain.Ingredient;
import com.mealPrep.mealPrep.domain.Member;
import com.mealPrep.mealPrep.domain.Recipe;
import com.mealPrep.mealPrep.domain.RecipeIngredient;
import com.mealPrep.mealPrep.dto.RecipeWriteRequestDTO;
import com.mealPrep.mealPrep.repository.IngredientRepository;
import com.mealPrep.mealPrep.repository.MemberRepository;
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
    private final MemberRepository memberRepository;
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

        Member memberId = memberRepository.findOneByMemberId(request.getMemberId());

        Recipe recipe = new Recipe();
        recipe.setCategory(request.getCategory()); // category 필드에 대한 setter 메서드를 호출하여 값을 설정합니다.
        recipe.setPrice(request.getTotalPrice());
        recipe.setAuthor(memberId.getNickname());
        recipe.setCalorie(request.getTotalKcal());
        recipe.setCooking_time(request.getTotalTime());
        recipe.setLikes(0L);
        recipe.setView(0L);
        recipe.setBody(request.getBody());
        recipe.setTitle(request.getTitle());
        recipe.setMember(memberId);

        recipeRepository.save(recipe);

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
