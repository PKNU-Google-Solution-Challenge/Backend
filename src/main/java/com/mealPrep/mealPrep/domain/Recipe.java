package com.mealPrep.mealPrep.domain;

import com.mealPrep.mealPrep.domain.Enum.Category;
import jakarta.persistence.Entity;
import lombok.Getter;

import java.util.List;

@Entity
@Getter
public class Recipe extends Board{

    private Category category;
    private Long price;
    private Long cooking_time;
    private Long calorie;
    private List<Ingredient> ingredients; // 이놈 굉장히 애매함 구조 잘 모르겠음
}
