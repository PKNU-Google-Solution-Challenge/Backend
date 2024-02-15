package com.mealPrep.mealPrep.dto;

import com.mealPrep.mealPrep.domain.Enum.CommentStates;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CommentRequestDTO {
    private String memberId;
    private CommentStates status;
    private String comment; //댓글 내용
}
