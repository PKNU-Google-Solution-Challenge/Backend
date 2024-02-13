package com.mealPrep.mealPrep.service;


import com.mealPrep.mealPrep.dto.CommentRequestDTO;
import com.mealPrep.mealPrep.domain.Board;
import com.mealPrep.mealPrep.domain.Comment;
import com.mealPrep.mealPrep.domain.Member;
import com.mealPrep.mealPrep.dto.CommentResponseDTO;
import com.mealPrep.mealPrep.dto.CommentUpdateRequestDTO;
import com.mealPrep.mealPrep.repository.CommentRepository;
import com.mealPrep.mealPrep.repository.BoardRepository;
import com.mealPrep.mealPrep.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

    /**
     * 댓글 작성
     */
    @Transactional
    public CommentResponseDTO createComment(Long boardId, CommentRequestDTO request){
        Member memberId = memberRepository.findOneByMemberId(request.getMemberId());
        Board board = boardRepository.findByBoardId(boardId);

        List<Comment> comments = commentRepository.findAllByBoardId(board);
        Comment comment = null;
        if(comments.isEmpty()){ //댓글이 아무것도 안달려있을때
            comment = Comment.builder()
                    .comment(request.getComment())
                    .commentState(request.getStatus())
                    .member(memberId)
                    .boardId(board)
                    .answerNumber(0L)
                    .parentNum(0L)
                    .ref(1L)
                    .refOrder(0L)
                    .step(0L)
                    .build();
        }else{  //댓글이 하나라도 있을 경우
            comment = Comment.builder()
                    .comment(request.getComment())
                    .commentState(request.getStatus())
                    .member(memberId)
                    .boardId(board)
                    .answerNumber(0L)
                    .parentNum(0L)
                    .ref(comments.size()+1L)
                    .refOrder(0L)
                    .step(0L)
                    .build();
        }

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
     * 대댓글 작성
     */
    @Transactional
    public CommentResponseDTO createRecomment(Long parentNum,Long boardId,CommentRequestDTO request){
        Optional<Comment> byId = commentRepository.findById(parentNum);
        Member memberId = memberRepository.findOneByMemberId(request.getMemberId());
        Board board = boardRepository.findByBoardId(boardId);
        Comment comment = null;
        if(byId.isPresent()){
            Comment parentComment = byId.get();   //부모 댓글
            Long reCommentStep = parentComment.getStep()+1L;

            List<Comment> allByRef = commentRepository.findAllByRef(parentComment.getRef());    //부모 댓글의 그룹 댓글 리스트
            Long childAnswerNum=0L;
            Long maxStep = Long.MIN_VALUE;
            for (Comment comment1 : allByRef) {
                childAnswerNum+=comment1.getAnswerNumber();
                if (maxStep < comment1.getStep()) {
                    maxStep = comment1.getStep(); //부모 댓글의 그룹내의 step컬럼 최댓값
                }
            }

            Long refOrder =0L;
            if(reCommentStep>maxStep){

            }else if(maxStep == reCommentStep){

            }else{

            }

            comment = Comment.builder()
                    .comment(request.getComment())
                    .commentState(request.getStatus())
                    .member(memberId)
                    .boardId(board)
                    .step(reCommentStep)
                    .parentNum(parentComment.getId())
                    .ref(parentComment.getRef())
                    .refOrder(1L)
                    .answerNumber(0L)
                    .build();
            //부모댓글 answerNum +1씩
            while(parentComment.getParentNum()!=0){
                Comment foundComment = commentRepository.findOneById(parentComment.getParentNum());
                foundComment.setAnswerNumber(foundComment.getAnswerNumber()+1L);
                parentComment=foundComment;
            }
        }
        commentRepository.save(comment);

        assert comment != null;
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

    @Transactional
    public Boolean updateComment(Long commentId, CommentUpdateRequestDTO request) {
        Optional<Comment> byId = commentRepository.findById(commentId);
        if(byId.isPresent()){
            Comment comment = byId.get();
            comment.setComment(request.getComment());
            comment.setCommentState(request.getStatus());
            return true;
        }
        return false;
    }
}
