package com.mealPrep.mealPrep.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mealPrep.mealPrep.domain.Enum.Category;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class RecipeViewDTO {
    private Long boardId;
    private String author;
    private String title;
    private String body;
    private String imgUrl;
    private Long views;
    private Long likes;
    private Long totalTime;
    private Long totalKcal;
    private Long totalPrice;
    private Category category;
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern="yyyy-MM-dd")
    private LocalDateTime createdAt;
    List<String> ingredients;
    List<CommentResponseDTO> comments;
}
