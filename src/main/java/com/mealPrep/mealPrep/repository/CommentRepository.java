package com.mealPrep.mealPrep.repository;

import com.mealPrep.mealPrep.domain.Board;
import com.mealPrep.mealPrep.domain.Comment;
import com.mealPrep.mealPrep.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByBoardId(Board boardId);
    List<Comment> findAllByRefOrder(Long refOrder);
    Comment findOneById(Long id);
    List<Comment> findAllByRef(Long ref);

    //부모댓글의 그룹내의 순서보다 큰 refOrder는 모두 +1 더해줍니다.
    List<Comment> findAllByBoardIdAndRefOrderIsGreaterThan(Board boardId,Long parentCommentRefOrder);
    List<Comment> findAllByMember(Member member);
}