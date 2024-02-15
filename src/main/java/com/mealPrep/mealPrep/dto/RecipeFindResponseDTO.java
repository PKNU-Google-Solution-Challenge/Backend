package com.mealPrep.mealPrep.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecipeFindResponseDTO {
    private Long recipeId;
    private String title;
    private String author;
    private LocalDateTime time;
    private Long view;
}
