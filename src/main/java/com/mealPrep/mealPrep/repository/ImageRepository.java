package com.mealPrep.mealPrep.repository;

import com.mealPrep.mealPrep.domain.Board;
import com.mealPrep.mealPrep.domain.Image;
import com.mealPrep.mealPrep.domain.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ImageRepository extends JpaRepository<Image,Long> {
    Optional<Image> findByBoardId(Board boardId);

    void deleteByBoardId(Board boardId);
}
