package com.mealPrep.mealPrep.dto;

import com.mealPrep.mealPrep.domain.Enum.Category;
import com.mealPrep.mealPrep.domain.RecipeIngredient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
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
