package com.mealPrep.mealPrep.domain;

import com.mealPrep.mealPrep.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Board extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long boardId;

    private String title;
    private String body;
    private String author;  //작성자

    @ManyToOne
    @JoinColumn(name="member_id")
    private Member member;

    @Column(columnDefinition = "integer default 0")
    private Long view;

    @Column(columnDefinition = "integer default 0")
    private Long likes;

    @OneToMany(mappedBy = "id", fetch = FetchType.LAZY)
    private List<Image> images = new ArrayList<>();
}