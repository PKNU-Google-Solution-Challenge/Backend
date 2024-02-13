package com.mealPrep.mealPrep.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mealPrep.mealPrep.domain.Enum.CommentStates;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CommentResponseDTO {
    private Long commentId;
    private String nickname;
    private Long answerNum;
    private Long parentNum;
    private Long ref;
    private Long refOrder;
    private Long step;
    private Long boardId;
    private CommentStates status;
    private String comment;
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern="yyyy-MM-dd")
    private LocalDateTime createdAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern="yyyy-MM-dd")
    private LocalDateTime modifiedAt;
}
