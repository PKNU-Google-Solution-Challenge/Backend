package com.mealPrep.mealPrep.repository;

import com.mealPrep.mealPrep.domain.Board;
import com.mealPrep.mealPrep.domain.Community;
import com.mealPrep.mealPrep.domain.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommunityRepository extends JpaRepository<Community,Long> {
}
