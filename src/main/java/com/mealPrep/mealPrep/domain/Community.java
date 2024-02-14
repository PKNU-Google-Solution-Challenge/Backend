package com.mealPrep.mealPrep.domain;

import jakarta.persistence.Entity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Builder
public class Community extends Board{

    public Community(){

    }
}
