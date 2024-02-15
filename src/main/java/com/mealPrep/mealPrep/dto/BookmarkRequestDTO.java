package com.mealPrep.mealPrep.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class BookmarkRequestDTO {
    private Long boardId;
    private Long memberId;
}
