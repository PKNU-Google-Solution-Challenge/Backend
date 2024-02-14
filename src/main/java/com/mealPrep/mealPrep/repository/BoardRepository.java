package com.mealPrep.mealPrep.repository;

import com.mealPrep.mealPrep.domain.Board;
import com.mealPrep.mealPrep.domain.Enum.Category;
import com.mealPrep.mealPrep.domain.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<Board,Long> {

    List<Recipe> findAllByOrderByCreatedAtDesc();
    Board findByBoardId(Long boardId);
    List<Recipe> findRecipesByCategory(Category category);
}

