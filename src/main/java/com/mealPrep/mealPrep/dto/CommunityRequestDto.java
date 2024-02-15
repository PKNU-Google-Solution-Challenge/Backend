package com.mealPrep.mealPrep.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mealPrep.mealPrep.domain.Member;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CommunityRequestDto {
    private String title;
    private String body;
//    private String author;
    private String memberId;
}
