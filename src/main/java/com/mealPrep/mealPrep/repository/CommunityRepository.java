package com.mealPrep.mealPrep.repository;

import com.mealPrep.mealPrep.domain.Board;
import com.mealPrep.mealPrep.domain.Community;
import com.mealPrep.mealPrep.domain.Image;
import com.mealPrep.mealPrep.domain.RecipeIngredient;
import com.mealPrep.mealPrep.dto.CommunityViewDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommunityRepository extends JpaRepository<Community,Long> {
    List<Community> findAllByOrderByCreatedAtDesc();
}
