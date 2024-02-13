package com.mealPrep.mealPrep.domain;

import com.mealPrep.mealPrep.domain.Enum.CommentStates;
import com.mealPrep.mealPrep.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Comment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String comment; // 댓글 내용

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board boardId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Member member; // 작성자

    private Long answerNumber;

    private Long ref;
    private Long refOrder;

    private Long parentNum;

    private Long step; //뭔지 기억 안남

    @Enumerated(EnumType.STRING)
    private CommentStates commentState;
}