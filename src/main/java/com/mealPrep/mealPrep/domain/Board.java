package com.mealPrep.mealPrep.domain;

import com.mealPrep.mealPrep.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Board extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long board_id;

    private String title;
    private String body;

    @Column(columnDefinition = "integer default 0")
    private Long view;

    @Column(columnDefinition = "integer default 0")
    private Long like;

    @OneToMany(mappedBy = "id", fetch = FetchType.LAZY)
    private List<Image> images = new ArrayList<>();
}
