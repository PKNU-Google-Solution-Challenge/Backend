package com.mealPrep.mealPrep.domain;

import com.mealPrep.mealPrep.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
public class BookMark extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bookmark_id")
    private Long id;

    private Long memberId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    public BookMark() {
    }

    public BookMark(Long userId, Board board) {
        this.memberId = userId;
        this.board = board;
    }
}
