package com.mealPrep.mealPrep.domain;

import com.mealPrep.mealPrep.domain.Enum.Category;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;

import java.util.List;

@Entity
@Getter
public class Recipe extends Board{

    private Category category;
    private Long price;
    private Long cooking_time;
    private Long calorie;

    @OneToMany(mappedBy = "recipe",cascade = CascadeType.ALL)
    private List<RecipeIngredient> ingredients; // 이놈 굉장히 애매함 구조 잘 모르겠음
}
