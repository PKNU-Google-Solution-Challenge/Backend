package com.mealPrep.mealPrep.service;


import com.mealPrep.mealPrep.dto.CommentRequestDTO;
import com.mealPrep.mealPrep.domain.Board;
import com.mealPrep.mealPrep.domain.Comment;
import com.mealPrep.mealPrep.domain.Member;
import com.mealPrep.mealPrep.dto.CommentResponseDTO;
import com.mealPrep.mealPrep.repository.CommentRepository;
import com.mealPrep.mealPrep.repository.BoardRepository;
import com.mealPrep.mealPrep.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;
    @Transactional
    public CommentResponseDTO createComment(Long boardId, CommentRequestDTO request){
        Member memberId = memberRepository.findOneByMemberId(request.getMemberId());
        Board board = boardRepository.findByBoardId(boardId);
        Comment comment = Comment.builder()
                .boardId(board)
                .member(memberId)
                .commentState(request.getStatus())
                .ref(request.getRef())
                .refOrder(request.getRefOrder())
                .step(request.getStep())
                .answerNumber(request.getAnswerNum())
                .parentNum(request.getParentNum())
                .comment(request.getComment())
                .build();
        commentRepository.save(comment);

        CommentResponseDTO commentResponse = CommentResponseDTO.builder()
                .commentId(comment.getId())
                .nickname(memberId.getNickname())
                .boardId(comment.getBoardId().getBoardId())
                .status(comment.getCommentState())
                .ref(comment.getRef())
                .refOrder(comment.getRefOrder())
                .step(comment.getStep())
                .answerNum(comment.getAnswerNumber())
                .parentNum(comment.getParentNum())
                .comment(comment.getComment())
                .createdAt(comment.getCreatedAt())
                .build();

        return commentResponse;
    }

    /**
     * 댓글 삭제
     */
    @Transactional
    public Boolean deleteComment(Long commentId) {
        Optional<Comment> byId = commentRepository.findById(commentId);
        if(byId.isPresent()){
            Comment comment = byId.get();
            commentRepository.delete(comment);
            return true;
        }
        return false;
    }
}
