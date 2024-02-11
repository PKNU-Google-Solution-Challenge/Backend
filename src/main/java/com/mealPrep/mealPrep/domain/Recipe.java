package com.mealPrep.mealPrep.domain;

import com.mealPrep.mealPrep.domain.Enum.Category;
import com.mealPrep.mealPrep.dto.RecipeWriteRequestDTO;
import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@Builder
@DiscriminatorValue("Recipe")
public class Recipe extends Board{

    private Category category;
    private Long price;
    private Long cooking_time;
    private Long calorie;

    @OneToMany(mappedBy = "recipe",cascade = CascadeType.ALL)
    private List<RecipeIngredient> ingredients; // 이놈 굉장히 애매함 구조 잘 모르겠음

    public Recipe() {

    }

//    public Recipe(RecipeWriteRequestDTO request){
//        this.category=request.getCategory();
//        this.price=request.getTotalPrice();
//        this.cooking_time=request.getTotalTime();
//        this.calorie=request.getTotalKcal();
//        }
}
