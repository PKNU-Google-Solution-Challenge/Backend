package com.mealPrep.mealPrep.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Image {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Long id;

    private String image_url;
}