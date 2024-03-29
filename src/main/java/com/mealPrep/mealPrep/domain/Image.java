package com.mealPrep.mealPrep.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Image {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "boardId")
    private Board boardId;
    private String image_url;
}