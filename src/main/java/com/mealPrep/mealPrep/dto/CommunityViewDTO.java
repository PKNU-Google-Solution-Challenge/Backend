package com.mealPrep.mealPrep.dto;

import com.mealPrep.mealPrep.domain.Member;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommunityViewDTO {
    private String title;
    private Member member;
    private Long boardId;
    private Long Likes;
    private LocalDateTime createdDate;
}
