package com.mealPrep.mealPrep.domain;

import com.mealPrep.mealPrep.domain.Enum.Category;
import com.mealPrep.mealPrep.dto.RecipeWriteRequestDTO;
import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@RequiredArgsConstructor
@DiscriminatorValue("Recipe")
public class Recipe extends Board{

    private Category category;
    private Long price;
    private Long cooking_time;
    private Long calorie;

    @OneToMany(mappedBy = "recipe",cascade = CascadeType.ALL)
    private List<RecipeIngredient> ingredients; // 이놈 굉장히 애매함 구조 잘 모르겠음

    public Recipe(RecipeWriteRequestDTO request){
        this.category=request.getCategory();
        this.price=request.getTotalPrice();
        this.cooking_time=request.getTotalTime();
        this.calorie=request.getTotalKcal();
        this.ingredients = new ArrayList<>();
        for (String ingredientName : request.getIngredients()) {
            RecipeIngredient recipeIngredient = new RecipeIngredient();
            // RecipeIngredient에 Ingredient 이름 설정
//            recipeIngredient.setName(ingredientName);
            // 그리고 나서 생성된 RecipeIngredient를 ingredients 리스트에 추가
            this.ingredients.add(recipeIngredient);
            }
        }
}
