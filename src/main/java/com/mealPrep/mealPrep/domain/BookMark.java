package com.mealPrep.mealPrep.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class BookMark {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bookmark_id")
    private Long id;

    private String userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    public BookMark() {
    }

    public BookMark(String userId, Board board) {
        this.userId = userId;
        this.board = board;
    }
}
