package com.mealPrep.mealPrep.dto;

import com.mealPrep.mealPrep.domain.Enum.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RecipeWriteRequestDTO {
    private Category category;
    private String title;
    private String body;
    private Long totalPrice;
    private Long totalTime;
    private Long totalKcal;
    private List<String> ingredients;
}
