package com.mealPrep.mealPrep.domain;

import com.mealPrep.mealPrep.domain.Enum.Category;
import com.mealPrep.mealPrep.dto.RecipeWriteRequestDTO;
import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@DiscriminatorValue("Recipe")
public class Recipe extends Board{

    private Category category;
    private Long price;
    private Long cooking_time;
    private Long calorie;

    @OneToMany(mappedBy = "recipe",cascade = CascadeType.ALL)
    private List<RecipeIngredient> ingredients; // 이놈 굉장히 애매함 구조 잘 모르겠음


    public Recipe(Category category, Long price, Long cookingTime, Long calorie, String author) {
        this.category = category;
        this.price = price;
        this.cooking_time = cookingTime;
        this.calorie = calorie;
    }

    public Recipe() {

    }
}