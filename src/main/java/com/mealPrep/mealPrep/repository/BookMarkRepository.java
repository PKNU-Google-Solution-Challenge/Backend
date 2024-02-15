package com.mealPrep.mealPrep.repository;

import com.mealPrep.mealPrep.domain.BookMark;
import com.mealPrep.mealPrep.domain.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookMarkRepository extends JpaRepository<BookMark,Long> {
    List<BookMark> findAllByMemberId(Long id);
}
